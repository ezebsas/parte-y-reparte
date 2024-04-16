package com.grupo2.parteyreparte.controllers;

import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.mappers.ProductMapper;
import com.grupo2.parteyreparte.mappers.UserMapper;
import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.security.config.JwtAuthenticationFilter;
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
    private StatsService statsService;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    Product product;

    @BeforeEach
    public void before() {
        product = new Product("Budin de pan","www.dns.asd.com",3,2,33.3);

        ProductMapper productMapper = new ProductMapper(new UserMapper());
        ProductDTO productDTO = productMapper.mapToProductDTO(product);

        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(productDTO);
        Mockito.doNothing().when(statsService).addInteraction();
    }

    @Test
    public void SE_RECIBE_201_CUANDO_USUARIO_PUBLICA_UN_ARTICULO() throws Exception {

        String product = """
                {    "name" : "Budin de pan",
                    "image": "www.dns.asd.com",
                    "link" : "www.amazon.com",
                    "maxPeople" : 3,
                    "minPeople" : 2,
                    "deadline" : "2024-04-15T20:53:46.129+00:00",
                    "totalCost" : 33.3,
                    "suscribers" : [],
                    "state": "OPEN"}""";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/products").content(product)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }
}