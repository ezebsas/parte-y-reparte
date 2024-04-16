package com.grupo2.parteyreparte.controllers;

import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.security.config.JwtAuthenticationFilter;
import com.grupo2.parteyreparte.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    Product product;

    @BeforeEach
    public void before() {
        product = new Product("Budin de pan","www.dns.asd.com",3,2,33.3);
        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(product);
    }

    @Test
    public void SE_RECIBE_201_CUANDO_USUARIO_PUBLICA_UN_ARTICULO() throws Exception {

        String product = "{" +
                "    \"name\" : \"Budin de pan\",\n" +
                "    \"image\": \"www.dns.asd.com\",\n" +
                "    \"link\" : \"www.amazon.com\",\n" +
                "    \"maxPeople\" : 3,\n" +
                "    \"minPeople\" : 2,\n" +
                "    \"deadline\" : \"2024-04-15T20:53:46.129+00:00\",\n" +
                "    \"totalCost\" : 33.3,\n" +
                "    \"suscribers\" : [],\n" +
                "    \"state\": \"OPEN\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/products").content(product)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }
}