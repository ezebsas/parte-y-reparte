package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.repositories.StatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StatsServiceTest {

    @Autowired
    StatsService statsService;

    @MockBean
    StatsRepository statsRepository;
    @BeforeEach
    public void init() {

    }


    @Test
    public void LA_CANTIDAD_DE_PUBLICACIONES_ES_3() {

        Mockito.when(statsRepository.findAllPublications()).thenReturn(
                java.util.List.of(Mockito.mock(Product.class),Mockito.mock(Product.class),Mockito.mock(Product.class))
        );

        assertEquals(3,statsService.getPublicationsCount());
    }



}