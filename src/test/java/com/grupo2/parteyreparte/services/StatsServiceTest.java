package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.repositories.ProductRepository;
import com.grupo2.parteyreparte.repositories.StatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StatsServiceTest {

    @Autowired
    StatsService statsService;

    @MockBean
    StatsRepository statsRepository;

    @MockBean
    UserService userService;

    @MockBean
    ProductRepository productRepository;

    @BeforeEach
    public void init() {

    }


    @Test // Solo para la parte de contador de publicaciones de la historia 5
    public void testAmountOfPublicationsIs3() {

        Mockito.when(productRepository.getAll()).thenReturn(
                java.util.List.of(Mockito.mock(Product.class),Mockito.mock(Product.class),Mockito.mock(Product.class))
        );

        assertEquals(3,statsService.getPublicationsCount());
    }





}