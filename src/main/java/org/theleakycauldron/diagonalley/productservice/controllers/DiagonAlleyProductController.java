package org.theleakycauldron.diagonalley.productservice.controllers;

import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyCreateCategoryRequestDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyCreateProductRequestDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyCreateProductResponseDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyDeleteProductRequestDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyDeleteProductResponseDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyGetProductsResponseDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyUpdateProductRequestDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyUpdateProductResponseDTO;
import org.theleakycauldron.diagonalley.productservice.services.DiagonAlleyProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.theleakycauldron.diagonalley.dtos.DiagonAlleyResponseDTO;

import jakarta.validation.Valid;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@RestController
@RequestMapping("/diagonalley/product-service")
public class DiagonAlleyProductController {

    private final DiagonAlleyProductService diagonAlleyService;

    public DiagonAlleyProductController(
        DiagonAlleyProductService diagonAlleyService
    ) {
        this.diagonAlleyService = diagonAlleyService;
    }

    @PostMapping("/product")
    public ResponseEntity<DiagonAlleyCreateProductResponseDTO> addProduct(@RequestBody @Valid DiagonAlleyCreateProductRequestDTO requestDTO) throws URISyntaxException {
        DiagonAlleyCreateProductResponseDTO responseDTO = diagonAlleyService.addProduct(requestDTO);
        responseDTO.setStatusCode(201);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @PostMapping("/category")
    public ResponseEntity<DiagonAlleyResponseDTO> addCategory(@RequestBody @Valid DiagonAlleyCreateCategoryRequestDTO requestDTO){
        DiagonAlleyResponseDTO responseDTO = diagonAlleyService.addCategory(requestDTO);
        responseDTO.setStatusCode(201);
        return ResponseEntity.status(201).body(responseDTO);

    }

    @PutMapping("/category")
    public ResponseEntity<DiagonAlleyResponseDTO> modifyCategory(@RequestBody @Valid DiagonAlleyUpdateProductRequestDTO requestDTO){
        DiagonAlleyUpdateProductResponseDTO responseDTO = diagonAlleyService.updateProduct(requestDTO);
        responseDTO.setStatusCode(200);
        return ResponseEntity.status(200).body(responseDTO);
    }

    @GetMapping("/product")
    public ResponseEntity<DiagonAlleyGetProductsResponseDTO> getProductByName(@RequestParam String query){
        DiagonAlleyGetProductsResponseDTO responseDTO = diagonAlleyService.getProductByKeywords(query);
        return ResponseEntity.status(200).body(responseDTO);
    }

    @DeleteMapping("/product")
    public ResponseEntity<DiagonAlleyDeleteProductResponseDTO> deleteProduct(@RequestBody @Valid DiagonAlleyDeleteProductRequestDTO requestDTO){
        DiagonAlleyDeleteProductResponseDTO responseDTO = diagonAlleyService.deleteProduct(requestDTO);
        return ResponseEntity.status(200).body(responseDTO);
    }
}
