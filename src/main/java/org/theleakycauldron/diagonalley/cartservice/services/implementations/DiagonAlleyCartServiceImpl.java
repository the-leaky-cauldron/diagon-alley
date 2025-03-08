package org.theleakycauldron.diagonalley.cartservice.services.implementations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyAddItemToCartRequestDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyAddItemToCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyDeleteCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyGetCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyPaymentResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyRemoveItemFromCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyUpdateCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.entities.Cart;
import org.theleakycauldron.diagonalley.cartservice.entities.CartItem;
import org.theleakycauldron.diagonalley.cartservice.exceptions.CartAlreadyEmptyException;
import org.theleakycauldron.diagonalley.cartservice.exceptions.InvalidCartToAddCartItemsException;
import org.theleakycauldron.diagonalley.cartservice.exceptions.NoCartFoundException;
import org.theleakycauldron.diagonalley.cartservice.exceptions.NoItemFoundInCartException;
import org.theleakycauldron.diagonalley.cartservice.repositories.DiagonAlleyRDBCartItemRepository;
import org.theleakycauldron.diagonalley.cartservice.repositories.DiagonAlleyRDBCartRepository;
import org.theleakycauldron.diagonalley.cartservice.services.DiagonAlleyCartService;
import org.theleakycauldron.diagonalley.commons.utils.DiagonAlleyUtils;
import org.theleakycauldron.diagonalley.orderservice.entities.Order;
import org.theleakycauldron.diagonalley.orderservice.entities.OrderItem;
import org.theleakycauldron.diagonalley.orderservice.entities.OrderStatus;
import org.theleakycauldron.diagonalley.orderservice.services.DiagonAlleyOrderService;
import org.theleakycauldron.diagonalley.paymentservice.services.DiagonAlleyPaymentService;

@Service
public class DiagonAlleyCartServiceImpl implements DiagonAlleyCartService {

    private final DiagonAlleyRDBCartRepository cartRepository;
    private final DiagonAlleyOrderService orderService;
    private final DiagonAlleyPaymentService paymentService;
    private final DiagonAlleyRDBCartItemRepository cartItemRepository;

    public DiagonAlleyCartServiceImpl(
        DiagonAlleyRDBCartRepository cartRepository,
        DiagonAlleyOrderService orderService,
        DiagonAlleyPaymentService paymentService,
        DiagonAlleyRDBCartItemRepository cartItemRepository
    ) {
        this.cartRepository = cartRepository;
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.cartItemRepository = cartItemRepository;
    }


    @Override
    public DiagonAlleyAddItemToCartResponseDTO addItemToCart(String userId, DiagonAlleyAddItemToCartRequestDTO request) {
        
        Cart cart = null;
        LocalDateTime now = LocalDateTime.now();
        
        // Building cart item for cart
        CartItem cartItem = CartItem.builder()
                                .productId(request.getProductUuid())
                                .quantity(request.getQuantity())
                                .unitPrice(request.getPrice())
                                .build();

        Optional<Cart> cartOptional = cartRepository.findCartByUserId(userId);

        // Checking whether cart is available or not. 
        // If not create a new cart add items into it
        if(cartOptional.isEmpty()){

            throw new InvalidCartToAddCartItemsException("Can't add cart item to invalid cart");

        }

        cart = cartOptional.get();
        cart.setUpdatedAt(now);
        cart.getItems().add(cartItem);
        cart.setTotalPrice(cart.getTotalPrice() + (request.getPrice() * request.getQuantity()));

        cartItem.setCart(cart);

        Cart updatedCart = cartRepository.save(cart);


        return DiagonAlleyUtils.convertCartToCartResponseDto(userId, request, updatedCart);

    }

    @Override
    public DiagonAlleyRemoveItemFromCartResponseDTO removeItemFromCart(String userId, String productId) {
        
        Optional<Cart> cartOptional = cartRepository.findCartByUserId(userId);

        if(cartOptional.isEmpty()) {
            throw new NoCartFoundException("No cart found");
        }
        
        Cart cart = cartOptional.get();

//        Stream<CartItem> cartItemsStream = cart.getItems().stream();

//        Predicate<CartItem> predicate = cartItem -> cartItem.getProductId().equals(productId);
//        CartItem cartItem = cartItemsStream.filter(item -> item.getProductId().equals(productId)).findFirst().orElseThrow(() -> new RuntimeException("Item not found in the cart"));
//        int cartItemQuantity = cartItem.getQuantity();
//        Double cartItemUnitPrice = cartItem.getUnitPrice();
//        List<CartItem> updatedCartList = cart.getItems().stream().filter(item -> !item.getProductId().equals(productId)).collect(Collectors.toCollection(ArrayList::new));
//        cart.setItems(updatedCartList);

        List<CartItem> cartItems = cart.getItems();

        int cartItemQuantity = 0;
        double cartItemUnitPrice = 0;

        for(CartItem cartItem : cartItems){
            if(cartItem.getProductId().equals(productId)){
                cartItemQuantity = cartItem.getQuantity();
                cartItemUnitPrice = cartItem.getUnitPrice();
            }
        }

        cartItems.removeIf(cartItem -> cartItem.getProductId().equals(productId));
        cart.setItems(cartItems);

        cart.setUpdatedAt(LocalDateTime.now());

        cart.setTotalPrice(cart.getTotalPrice() - (cartItemQuantity * cartItemUnitPrice));
        
        cartRepository.save(cart);

        return DiagonAlleyUtils.convertProductToCartResponseDTO(cart.getUuid().toString(), productId);
    }

    @Override
    public DiagonAlleyUpdateCartResponseDTO updateItemQuantity(String userId, String productId, int quantity) {
    
        Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(() -> new NoCartFoundException("No cart found"));
        LocalDateTime now = LocalDateTime.now();
        cart.setUpdatedAt(now);

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NoItemFoundInCartException("No such item found in cart"));

        int cartItemQuantity = cartItem.getQuantity();

        if (quantity == 0) {
            // ✅ Explicitly remove the item from the database
            cartItemRepository.delete(cartItem);
            cart.getItems().remove(cartItem);
        } else {
            cartItem.setQuantity(quantity);
        }

        // ✅ Update the total price in cart
        double effectivePrice = cart.getTotalPrice() - (cartItemQuantity * cartItem.getUnitPrice()) + (quantity * cartItem.getUnitPrice());
        cart.setTotalPrice(effectivePrice);

        // ✅ Save cart
        cartRepository.save(cart);

        return DiagonAlleyUtils.convertCartToUpdateCartResponseDTO(cart, productId);

    }

    @Override
    public DiagonAlleyGetCartResponseDTO getCart(String userId) {
        
        LocalDateTime now = LocalDateTime.now();
        Optional<Cart> cartOptional = cartRepository.findCartByUserId(userId);

        Cart cart;
        if(cartOptional.isEmpty()) {

            cart = Cart.builder()
                        .userId(userId)
                        .uuid(UUID.randomUUID())
                        .items(new ArrayList<>())
                        .isDeleted(false)
                        .createdAt(now)
                        .updatedAt(now)
                        .build();
            cartRepository.save(cart);
            
        }else {
            cart = cartOptional.get();
        }

        return DiagonAlleyUtils.convertCartToGetCartResponseDTO(cart);

    }

    @Override
    public DiagonAlleyDeleteCartResponseDTO clearCart(String userId) {

        Optional<Cart> cartOptional = cartRepository.findCartByUserId(userId);
        if(cartOptional.isEmpty()) {
            throw new CartAlreadyEmptyException("Cart is already empty");
        }

        Cart cart = cartOptional.get();
        cartRepository.delete(cart);

        return DiagonAlleyUtils.convertCartToDeleteCartResponseDTO(cart);
    }

    @Override
    public DiagonAlleyPaymentResponseDTO checkout(String userId) {

        /*
         * 1. Get the cart
         * 2. Create an order by delegating task to order service
         * 3. Create the payment by delegating task to payment service
         * 4. clear the cart
         * 
         */

        LocalDateTime now = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();

        Optional<Cart> cartOptional = cartRepository.findCartByUserId(userId);

        // TODO: Create new Exception
        Cart cart = cartOptional.orElseThrow(() -> new NoCartFoundException("No cart found"));

        double totalPrice = cart.getTotalPrice();
        List<CartItem> cartItems = cart.getItems();

        Order order = Order.builder()
                        .userId(userId)
                        .billAmount(totalPrice)
                        .orderItems(cartItems.stream().map(cartItem -> OrderItem.builder()
                                                                    .productId(cartItem.getProductId())
                                                                    .quantity(cartItem.getQuantity())
                                                                    .unitPrice(cartItem.getUnitPrice())
                                                                    .totalPrice(cartItem.getQuantity() * cartItem.getUnitPrice())
                                                                    .build()
                                    ).collect(Collectors.toList())
                        )
                        .orderStatus(OrderStatus.CREATED)
                        .trackingNumber(uuid.toString())
                        .createdAt(now)
                        .updatedAt(now)
                        .isDeleted(false)
                        .uuid(uuid)
                        .build();
        
        orderService.createOrder(order);
        cartRepository.delete(cart);

        String paymentLink = paymentService.getPaymentLink(order);

        return DiagonAlleyPaymentResponseDTO.builder()
                    .paymentLink(paymentLink)
                    .build();
    }
}
