package com.grupo2.parteyreparte.controllers;

import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.mappers.ProductMapper;
import com.grupo2.parteyreparte.mappers.UserMapper;
import com.grupo2.parteyreparte.models.Interaction;
import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.ProductUnit;
import com.grupo2.parteyreparte.security.config.JwtAuthenticationFilter;
import com.grupo2.parteyreparte.services.InteractionService;
import com.grupo2.parteyreparte.services.ProductService;
import com.grupo2.parteyreparte.services.StatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InteractionService interactionService;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    Product product;

    @BeforeEach
    public void before() {
        product = new Product("Apple pie","www.dns.asd.com",3,2,33.3, 10.0, ProductUnit.KILOGRAM);

        ProductMapper productMapper = new ProductMapper(new UserMapper());
        ProductDTO productDTO = productMapper.mapToProductDTO(product);

        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(productDTO);
    }

    @Test
    public void test201WhenUserPublishesAProduct() throws Exception {

        String productJson = "{" +
                "\"name\": \"Apple pie\"," +
                "\"image\": \"www.dns.asd.com\"," +
                "\"link\": \"www.amazon.com\"," +
                "\"maxPeople\": 3," +
                "\"minPeople\": 2," +
                "\"deadline\": null," +
                "\"totalCost\": 33.3," +
                "\"subscribers\": []," +
                "\"state\": \"OPEN\"" +
                "}";


        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/products").content(productJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }
    @Test
    public void test200ForGetProducts() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/products")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}