package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.repositories.redis.StatsRedisRepository;
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
    StatsRedisRepository statsRedisRepository;

    @MockBean
    ProductService productServiceMock;

    @BeforeEach
    public void init() {

    }


    @Test // History 5
    public void amountOfPublicationsIs3() {

        Mockito.when(productServiceMock.countProducts()).thenReturn(3);

        assertEquals(3,statsService.getPublicationsCount());
        Mockito.verify(productServiceMock,Mockito.times(1)).countProducts();
    }





}