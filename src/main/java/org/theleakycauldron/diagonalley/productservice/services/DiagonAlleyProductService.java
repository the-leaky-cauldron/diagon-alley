package org.theleakycauldron.diagonalley.productservice.services;

import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyCreateCategoryRequestDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyCreateCategoryResponseDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyCreateProductRequestDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyCreateProductResponseDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyDeleteProductRequestDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyDeleteProductResponseDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyGetProductsResponseDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyUpdateProductRequestDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyUpdateProductResponseDTO;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

public interface DiagonAlleyProductService {
    DiagonAlleyCreateProductResponseDTO addProduct(DiagonAlleyCreateProductRequestDTO requestDTO);
    DiagonAlleyCreateCategoryResponseDTO addCategory(DiagonAlleyCreateCategoryRequestDTO requestDTO);
    DiagonAlleyUpdateProductResponseDTO updateProduct(DiagonAlleyUpdateProductRequestDTO requestDTO);
    DiagonAlleyGetProductsResponseDTO getProductByKeywords(String query);
    DiagonAlleyDeleteProductResponseDTO deleteProduct(DiagonAlleyDeleteProductRequestDTO requestDTO);
}
