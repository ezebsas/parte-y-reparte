package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.ProductState;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.repositories.ProductRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {


    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepositoryMock;

    @MockBean
    UserService userServiceMock;

    Product product;

    User userTest;

    @BeforeEach
    public void init() {

        userTest = new User("Migue",3,"fed@asd.com");
        product = new Product("Coca","www.dns.a/a.img",3,2,33.2);

        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        Mockito.when(productRepositoryMock.update(Mockito.any(),Mockito.any(Product.class))).thenReturn(product);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getName()).thenReturn(userTest.getName());
        Mockito.when(authentication.getPrincipal()).thenReturn(userTest);

    }

    @Test
    void SE_CREA_UN_PRODUCTO_CORRECTAMENTE() {

        String test_id = "1";

        Mockito.when(productRepositoryMock.existsById(Mockito.anyString())).thenReturn(false);
        Mockito.doAnswer(invocationOnMock -> {
            Product product = (Product) invocationOnMock.getArguments()[0];
            product.setId(test_id);
            return product;
        }).when(productRepositoryMock).createProduct(product);


        Product productCreated = productService.createProduct(product);

        assertTrue(productCreated.getId().contains(test_id));
        assertDoesNotThrow(() -> productService.createProduct(product));
    }

    @Test
    void SUSCRIPCION_CORRECTA_A_UN_PRODUCTO() {
        String test_id = "1";

        Mockito.when(userServiceMock.getLoggedUser()).thenReturn(userTest);

        productService.subscribeLoggedUser(test_id);

        assertEquals(product.getSuscribers().size(),1);
        assertEquals(product.getSuscribers().get(0).getName(),"Migue");
    }

    @Test
    void CIERRE_DE_PUBLICACION_CERRADA_NOTIFICACION_A_USUARIOS_CORRECTA() {

        List<User> subscribers = new ArrayList<>();
        subscribers.add(new User("Ernesto",13,"ee@asd.com"));
        subscribers.add(new User("Guille",23,"gg@asd.com"));
        product.setSuscribers(subscribers);
        userTest.setProductsPublished(List.of(product));

        productService.closeProduct("1");

        assertEquals(1,subscribers.get(0).getNotifications().size());
        assertEquals(1,subscribers.get(1).getNotifications().size());
        assertEquals(ProductState.CLOSED, product.getState());
    }

    @Test
    void CIERRE_DE_PUBLICACION_INCOMPLETA_NOTIFICACION_A_USUARIOS_CORRECTA() {

        List<User> subscribers = new ArrayList<>();
        subscribers.add(new User("Ernesto",13,"ee@asd.com"));
        product.setSuscribers(subscribers);

        userTest.setProductsPublished(List.of(product));

        productService.closeProduct("1");

        assertEquals(1,subscribers.get(0).getNotifications().size());
        assertEquals(ProductState.INCOMPLETED, product.getState());
    }
}