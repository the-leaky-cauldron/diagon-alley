package org.theleakycauldron.diagonalley.productservice.controllers;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyCreateProductRequestDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyCreateProductResponseDTO;
import org.theleakycauldron.diagonalley.productservice.services.DiagonAlleyProductService;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DiagonAlleyProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DiagonAlleyProductControllerTests {
    
    @MockitoBean
    private DiagonAlleyProductService productServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private DiagonAlleyCreateProductRequestDTO requestDTO;

    public DiagonAlleyProductControllerTests() {
        this.requestDTO = new DiagonAlleyCreateProductRequestDTO(
                "Nimbus 2000",
                "A fast broom",
                "Quality Brooms",
                "Brooms",
                1250.0,
                0.0,
                "https://example.com/broom.png",
                java.util.List.of("sports", "magic"),
                4.9
        );
    }

    @Test
    void addProductTest_Success() throws Exception {

        var responseDTO = DiagonAlleyCreateProductResponseDTO.builder()
                            .uuid(UUID.randomUUID().toString())
                            .response("Product: " + requestDTO.getName() + " has been created").build();

        when(productServiceMock.addProduct(any(DiagonAlleyCreateProductRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(
            post("/product-service/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO))
        )
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.response").value("Product: " + requestDTO.getName() + " has been created"));
    }
}
