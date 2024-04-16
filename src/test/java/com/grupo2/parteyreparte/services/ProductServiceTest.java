package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.exceptions.ProductFullException;
import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.ProductState;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    User userTest;

    @BeforeEach
    public void init() {

        userTest = new User("Migue",3,"fed@asd.com");

        Mockito.when(userServiceMock.getLoggedUser()).thenReturn(userTest);
    }

    @Test // Test de servicio para historia 1
    void SE_CREA_UN_PRODUCTO_CORRECTAMENTE() {

        String test_id = "1";
        Product cocaProduct = new Product("Coca","www.dns.a/a.img",3,2,33.2);

        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.of(cocaProduct));
        Mockito.when(productRepositoryMock.update(Mockito.any(),Mockito.any(Product.class))).thenReturn(cocaProduct);

        Mockito.when(productRepositoryMock.existsById(Mockito.anyString())).thenReturn(false);
        Mockito.doAnswer(invocationOnMock -> {
            Product product = (Product) invocationOnMock.getArguments()[0];
            product.setId(test_id);
            return product;
        }).when(productRepositoryMock).createProduct(cocaProduct);


        ProductDTO productCreated = productService.createProduct(cocaProduct);

        assertTrue(productCreated.getId().contains(test_id));
        assertEquals(1,userTest.getProductsPublished().size());
    }

    @Test // Test de servicio para historia 2
    void SUSCRIPCION_CORRECTA_A_UN_PRODUCTO() {
        String test_id = "1";
        String pastFrola = "Pasta frola";

        Product pastaFrolaProduct = new Product(pastFrola,"www.dns.a/a.img",5,2,33.2);

        Mockito.when(productRepositoryMock.update(Mockito.any(),Mockito.any(Product.class))).thenReturn(pastaFrolaProduct);
        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.of(pastaFrolaProduct));


        Mockito.when(userServiceMock.getLoggedUser()).thenReturn(userTest);

        productService.subscribeLoggedUser(test_id);

        assertEquals(pastaFrolaProduct.getSuscribers().size(),1);
        assertEquals(pastaFrolaProduct.getSuscribers().get(0).getName(),"Migue");
        assertEquals(userTest.getProductsSubscribed().get(0).getName(), pastFrola);
    }

    // Tests de servicio para historia 4
    @Test
    void CIERRE_DE_PUBLICACION_CERRADA_NOTIFICACION_A_USUARIOS_CORRECTA() {

        Product asadoProduct = new Product("Asado","www.dns.a/a.img",5,2,33.2);

        List<User> subscribers = new ArrayList<>();
        subscribers.add(new User("Ernesto",13,"ee@asd.com"));
        subscribers.add(new User("Guille",23,"gg@asd.com"));
        asadoProduct.setSuscribers(subscribers);
        userTest.setProductsPublished(List.of(asadoProduct));

        Mockito.when(productRepositoryMock.update(Mockito.any(),Mockito.any(Product.class))).thenReturn(asadoProduct);
        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.of(asadoProduct));



        productService.closeProduct("1");

        assertEquals(1,subscribers.get(0).getNotifications().size());
        assertEquals(1,subscribers.get(1).getNotifications().size());
        assertEquals(ProductState.CLOSED, asadoProduct.getState());
    }

    @Test
    void CIERRE_DE_PUBLICACION_INCOMPLETA_NOTIFICACION_A_USUARIOS_CORRECTA() {

        Product packBicisProduct = new Product("Bicis x100","www.dns.a/a.img",5,2,33.2);

        List<User> subscribers = new ArrayList<>();
        subscribers.add(new User("Ernesto",13,"ee@asd.com"));
        packBicisProduct.setSuscribers(subscribers);

        userTest.setProductsPublished(List.of(packBicisProduct));
        Mockito.when(productRepositoryMock.update(Mockito.any(),Mockito.any(Product.class))).thenReturn(packBicisProduct);
        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.of(packBicisProduct));

        productService.closeProduct("1");

        assertEquals(1,subscribers.get(0).getNotifications().size());
        assertEquals(ProductState.INCOMPLETED, packBicisProduct.getState());
    }

    @Test // Parte de la historia 6
    void ARROJA_EXCEPCION_SE_QUIERE_SUSCRIBIR_UNO_MAS_AL_PRODUCTO() {

        String test_id = "1";
        String pastFrola = "Pasta frola";

        Product pastaFrolaProduct = new Product(pastFrola,"www.dns.a/a.img",2,1,33.2);

        pastaFrolaProduct.setSuscribers(List.of(
                Mockito.mock(User.class),
                Mockito.mock(User.class)
        ));

        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.of(pastaFrolaProduct));


        assertThrows(ProductFullException.class, () -> productService.subscribeLoggedUser("1"));
    }
}