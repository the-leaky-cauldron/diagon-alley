package org.theleakycauldron.diagonalley.productservice.services;

import com.fasterxml.uuid.impl.TimeBasedReorderedGenerator;
import org.springframework.stereotype.Service;
import org.theleakycauldron.diagonalley.commons.utils.DiagonAlleyUtils;
import org.theleakycauldron.diagonalley.outboxservice.entities.*;
import org.theleakycauldron.diagonalley.outboxservice.repositories.*;
import org.theleakycauldron.diagonalley.outboxservice.services.*;
import org.theleakycauldron.diagonalley.productservice.dtos.*;
import org.theleakycauldron.diagonalley.productservice.entities.*;
import org.theleakycauldron.diagonalley.productservice.entities.documents.ProductDocument;
import org.theleakycauldron.diagonalley.productservice.exceptions.*;
import org.theleakycauldron.diagonalley.productservice.repositories.*;

import java.time.LocalDateTime;
import java.util.*;

import static java.time.LocalDateTime.now;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Service
public class DiagonAlleyProductServiceImpl implements DiagonAlleyProductService {

    private final DiagonAlleyRDBProductRepository diagonAlleyRDBProductRepository;
    private final DiagonAlleyRDBCategoryRepository diagonAlleyRDBCategoryRepository;
    private final TimeBasedReorderedGenerator timeBasedReorderedGenerator;
    private final DiagonAlleyRDBOutboxRepository diagonAlleyRDBOutboxRepository;
    private final DiagonAlleyOutboxEventPublisher diagonAlleyOutboxEventPublisher;
    private final DiagonAlleyRDBManufacturerRepository  diagonAlleyRDBManufacturerRepository;
    private final DiagonAlleyRDBPriceRepository diagonAlleyRDBPriceRepository;
    private final DiagonAlleyElasticProductRepository diagonAlleyElasticProductRepository;

    public DiagonAlleyProductServiceImpl(
            DiagonAlleyRDBProductRepository diagonAlleyRDBProductRepository,
            DiagonAlleyRDBCategoryRepository diagonAlleyRDBCategoryRepository,
            TimeBasedReorderedGenerator timeBasedReorderedGenerator,
            DiagonAlleyRDBOutboxRepository diagonAlleyRDBOutboxRepository,
            DiagonAlleyOutboxEventPublisher diagonAlleyOutboxEventPublisher,
            DiagonAlleyRDBManufacturerRepository diagonAlleyRDBManufacturerRepository,
            DiagonAlleyRDBPriceRepository diagonAlleyRDBPriceRepository,
            DiagonAlleyElasticProductRepository diagonAlleyElasticProductRepository ){
        this.diagonAlleyRDBProductRepository = diagonAlleyRDBProductRepository;
        this.diagonAlleyRDBCategoryRepository = diagonAlleyRDBCategoryRepository;
        this.timeBasedReorderedGenerator = timeBasedReorderedGenerator;
        this.diagonAlleyRDBOutboxRepository = diagonAlleyRDBOutboxRepository;
        this.diagonAlleyOutboxEventPublisher = diagonAlleyOutboxEventPublisher;
        this.diagonAlleyRDBManufacturerRepository = diagonAlleyRDBManufacturerRepository;
        this.diagonAlleyRDBPriceRepository = diagonAlleyRDBPriceRepository;
        this.diagonAlleyElasticProductRepository = diagonAlleyElasticProductRepository;
    }


    @Override
    public DiagonAlleyCreateProductResponseDTO addProduct(DiagonAlleyCreateProductRequestDTO requestDTO) throws CategoryNotFoundException, ProductAlreadyExistsException{

        String productName = requestDTO.getName();
        String category = requestDTO.getProductCategory();
        UUID productUuid = timeBasedReorderedGenerator.generate();
        UUID priceUuid = timeBasedReorderedGenerator.generate();
        UUID manufacturerUuid = timeBasedReorderedGenerator.generate();
        LocalDateTime now = now();

        Optional<ProductJpaEntity> product = diagonAlleyRDBProductRepository.findByName(productName);

        if(product.isPresent()){
            throw new ProductAlreadyExistsException("Could not add product, product already exists");
        }

        Optional<ProductCategory> productCategory = diagonAlleyRDBCategoryRepository.findByName(category);

        if(productCategory.isEmpty()){
            throw new CategoryNotFoundException("Could not add product, category does not exist");
        }

        List<String> productTags = requestDTO.getTags();

        productTags.add(productUuid.toString());

        ProductJpaEntity newProductJpaEntity = ProductJpaEntity.builder()
                .name(productName)
                .uuid(productUuid)
                .createdAt(now)
                .updatedAt(now)
                .description(requestDTO.getDescription())
                .imageURL(requestDTO.getImageUrl())
                .price(
                        Price.builder()
                                .uuid(priceUuid)
                                .createdAt(now)
                                .updatedAt(now)
                                .isDeleted(false)
                                .amount(requestDTO.getPrice())
                                .discount(requestDTO.getDiscount())
                                .build()
                )
                .manufacturer(
                        Manufacturer.builder()
                                .uuid(manufacturerUuid)
                                .createdAt(now)
                                .updatedAt(now)
                                .isDeleted(false)
                                .name(requestDTO.getManufacturer())
                                .build()
                )
                .productCategory(productCategory.get())
                .tags(productTags)
                .rating(requestDTO.rating)
                .build();

        ProductJpaEntity savedProductJpaEntity = diagonAlleyRDBProductRepository.save(newProductJpaEntity);

        Outbox outbox = Outbox.builder()
                .productJpaEntity(savedProductJpaEntity)
                .uuid(productUuid)
                .createdAt(now)
                .updatedAt(now)
                .build();

        Outbox savedOutbox = diagonAlleyRDBOutboxRepository.save(outbox);

//        diagonAlleyOutboxEventPublisher.publishOutboxEvent(savedOutbox);

        return DiagonAlleyCreateProductResponseDTO.builder()
                .createdAt(now)
                .response("Product: " + productName + " has been created")
                .uuid(savedOutbox.getUuid().toString())
                .build();

    }

    @Override
    public DiagonAlleyCreateCategoryResponseDTO addCategory(DiagonAlleyCreateCategoryRequestDTO requestDTO) {

        String category = requestDTO.getCategory();
        UUID categoryUuid = timeBasedReorderedGenerator.generate();
        LocalDateTime now = now();
        Optional<ProductCategory> productCategory = diagonAlleyRDBCategoryRepository.findByName(category);

        if(productCategory.isPresent()){
            throw new CategoryAlreadyExistsException("Could not add category, category already exists");
        }

        ProductCategory newCategory = ProductCategory.builder()
                .name(category)
                .uuid(categoryUuid)
                .createdAt(now)
                .updatedAt(now)
                .isDeleted(false)
                .build();

        diagonAlleyRDBCategoryRepository.save(newCategory);

        return DiagonAlleyCreateCategoryResponseDTO.builder()
                .response(category)
                .createdAt(now)
                .build();

    }

    @Override
    public DiagonAlleyUpdateProductResponseDTO updateProduct(DiagonAlleyUpdateProductRequestDTO requestDTO) {

        Optional<ProductJpaEntity> productOptional;

        if(requestDTO.getUuid() != null){
            productOptional = diagonAlleyRDBProductRepository.findByUuid(UUID.fromString(requestDTO.getUuid()));
        }else if(requestDTO.getName() != null && !requestDTO.getName().isBlank()){
            productOptional = diagonAlleyRDBProductRepository.findByName(requestDTO.getName());
        }
        else{
            throw new RuntimeException("Specify either UUID or product name");
        }

        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Could not update product, product does not exist");
        }

        LocalDateTime now = now();
        ProductJpaEntity productJpaEntity = productOptional.get();
        String description = requestDTO.getDescription();
        String imageUrl = requestDTO.getImageUrl();
        String manufacturer = requestDTO.getManufacturer();
        String name = requestDTO.getName();
        double price = requestDTO.getPrice();
        double discount = requestDTO.getDiscount();
        double rating = requestDTO.getRating();
        List<String> tags = requestDTO.getTags();

        if(name != null){
            productJpaEntity.setName(name);
        }

        if(description != null){
            productJpaEntity.setDescription(description);
        }

        if(imageUrl != null){
            productJpaEntity.setImageURL(imageUrl);
        }

        if(manufacturer != null){
            diagonAlleyRDBManufacturerRepository.delete(productJpaEntity.getManufacturer());
            Manufacturer manufacturer1 = Manufacturer.builder()
                    .name(manufacturer)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            productJpaEntity.setManufacturer(manufacturer1);
        }

        if(price != 0){
            diagonAlleyRDBPriceRepository.delete(productJpaEntity.getPrice());
            Price price1 = Price.builder()
                    .amount(price)
                    .discount(discount)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            productJpaEntity.setPrice(price1);
        }

        if(rating != 0){
            productJpaEntity.setRating(rating);
        }

        if(tags != null){
            productJpaEntity.setTags(tags);
        }

        productJpaEntity.setUpdatedAt(now);

        ProductJpaEntity updatedProductJpaEntity = diagonAlleyRDBProductRepository.save(productJpaEntity);

//        diagonAlleyOutboxEventPublisher.publishOutboxUpdateEvent(product.getUuid())s

        return DiagonAlleyUpdateProductResponseDTO.builder()
                .response("Product: " + updatedProductJpaEntity.getName() + " has been updated")
                .uuid(updatedProductJpaEntity.getUuid().toString())
                .createdAt(now)
                .build();

    }

    @Override
    public DiagonAlleyGetProductsResponseDTO getProductByKeywords(String query) {

         List<ProductDocument> productDocuments = diagonAlleyElasticProductRepository.findProductByTagsEquals(Arrays.stream(query.split(" ")).toList());

         if(productDocuments.isEmpty()){
             throw new ProductNotFoundException("Could not find product with the given tags");
         }

        DiagonAlleyUtils.convertProductToGetProductsResponseDTOs(productDocuments);

        return DiagonAlleyGetProductsResponseDTO.builder()
                .diagonAlleyGetProductResponseDTOList(DiagonAlleyUtils.convertProductToGetProductsResponseDTOs(productDocuments).getDiagonAlleyGetProductResponseDTOList())
                .build();

    }


    /**
     * {@link DiagonAlleyDeleteProductRequestDTO}
     * @param requestDTO which has the product Uuid that needs to be deleted
     */

    @Override
    public DiagonAlleyDeleteProductResponseDTO deleteProduct(DiagonAlleyDeleteProductRequestDTO requestDTO){
        UUID uuid = requestDTO.getUuid();
        Optional<ProductJpaEntity> previousProductOptional = diagonAlleyRDBProductRepository.findByUuid(uuid);
        if(previousProductOptional.isEmpty()){
            throw new ProductNotFoundException("Product not found");
        }
        ProductJpaEntity previousProduct = previousProductOptional.get();
        if(previousProduct.isDeleted()){
            throw new ProductAlreadyDeletedException("Product already deleted!");
        }

        previousProduct.setDeleted(true);
        diagonAlleyRDBProductRepository.save(previousProduct);
        diagonAlleyOutboxEventPublisher.publishOutboxDeleteEvent(uuid);

        return DiagonAlleyDeleteProductResponseDTO.builder()
                .createdAt(LocalDateTime.now())
                .statusCode(200)
                .message("Product deleted successfully in database")
                .build();
    }


}
