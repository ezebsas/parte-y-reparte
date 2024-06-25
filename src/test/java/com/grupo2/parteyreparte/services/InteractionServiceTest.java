package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.models.Interaction;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.repositories.redis.InteractionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InteractionServiceTest {

    User userTest;

    @Autowired
    InteractionService interactionService;

    @MockBean
    UserService userServiceMock;

    @MockBean
    InteractionRepository interactionRepositoryMock;

    @MockBean
    StatsService statsServiceMock;

    @BeforeEach
    public void init() {

        userTest = new User("Migue",3,"fed@asd.com");

        Mockito.when(userServiceMock.getLoggedUser()).thenReturn(userTest);
    }
    @Test
    void addedInteraction() {

        interactionService.addInteraction(Interaction.InteractionType.PRODUCT_CREATION);

        Mockito.verify(interactionRepositoryMock,Mockito.times(1)).save(Mockito.any(Interaction.class));
    }
}