package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.exceptions.EntityNotFoundException;
import com.grupo2.parteyreparte.mappers.NotificationMapper;
import com.grupo2.parteyreparte.mappers.ProductMapper;
import com.grupo2.parteyreparte.mappers.UserMapper;
import com.grupo2.parteyreparte.models.Notification;
import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.ProductUnit;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.repositories.mongo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    ProductMapper productMapperMock;
    @MockBean
    UserMapper userMapperMock;
    @MockBean
    NotificationMapper notificationMapperMock;
    @MockBean
    UserRepository userRepositoryMock;

    @MockBean
    Authentication authentication;
    @MockBean
    SecurityContext securityContext;
    User userTest;

    @BeforeEach
    public void init() {

        userTest = new User("Migue",3,"fed@asd.com");
        userTest.setId("1");

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn("1");
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(userRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(userTest));
    }

    @Test
    void publishAProductSuccessfully() {
        Product cocaProduct = new Product("Coca","www.dns.a/a.img",3,2,33.2, 10.0, ProductUnit.UNIT);

        Mockito.when(userRepositoryMock.save(Mockito.any())).thenReturn(userTest);

        userService.publishProduct(cocaProduct);

        assertEquals(1,userTest.getProductsPublished().size());
        Mockito.verify(userRepositoryMock,Mockito.times(1)).save(userTest);
    }

    @Test
    void editUserSuccessfully() {
        String newName = "Miguelito";

        UserDTO userToUpdate =  new UserDTO();
        userToUpdate.setName(newName);

        userService.updateUser(userToUpdate);

        assertEquals(newName,userTest.getName());
    }

    @Test
    void deleteProductUnsuccessfully() {
        assertThrows(EntityNotFoundException.class,() -> userService.deleteUserProductById("1"));
    }

    @Test
    void loggedUserSuscribeToProductSuccessfully() {
        Product noganetKeyword = new Product("Storm","www.dns.a/a.img",2,1,333.2, 10.0, ProductUnit.UNIT);
        User arthas = new User("Arthas",19,"a@a");
        noganetKeyword.setOwner(arthas);

        Mockito.when(userRepositoryMock.save(Mockito.any())).thenReturn(userTest);

        userService.subscribeToProduct(noganetKeyword);

        assertEquals(1,userTest.getProductsSubscribed().size());

    }

    @Test
    void userIsNotified3Times() {

        userService.notifyUser(userTest,Mockito.mock(Notification.class));
        userService.notifyUser(userTest,Mockito.mock(Notification.class));
        userService.notifyUser(userTest,Mockito.mock(Notification.class));

        assertEquals(3,userTest.getNotifications().size());
    }


}