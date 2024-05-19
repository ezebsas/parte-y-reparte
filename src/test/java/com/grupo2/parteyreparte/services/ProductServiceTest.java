package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.exceptions.ProductFullException;
import com.grupo2.parteyreparte.mappers.ProductMapper;
import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.ProductState;
import com.grupo2.parteyreparte.models.ProductUnit;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.repositories.ProductRepositoryDepre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {


    @Autowired
    ProductService productService;

    @MockBean
    ProductRepositoryDepre productRepositoryMock;

    @MockBean
    UserService userServiceMock;

    @MockBean
    ProductMapper productMapperMock;

    User userTest;

    @BeforeEach
    public void init() {

        userTest = new User("Migue",3,"fed@asd.com");

        Mockito.when(userServiceMock.getLoggedUser()).thenReturn(userTest);
    }

    @Test // Test de servicio para historia 1
    void testCreateAProductSuccessfully() {

        String test_id = "1";
        Product cocaProduct = new Product("Coca","www.dns.a/a.img",3,2,33.2, 10.0, ProductUnit.UNIT);
        cocaProduct.setDeadline(LocalDateTime.now().plusDays(2));
        ProductDTO cocaProductDTO = new ProductDTO("Coca","www.dns.a/a.img",3,2,33.2, 10, ProductUnit.UNIT);

        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(cocaProduct);
        Mockito.when(productRepositoryMock.update(Mockito.any(),Mockito.any(Product.class))).thenReturn(cocaProduct);

        Mockito.when(productMapperMock.mapToProduct(cocaProductDTO)).thenReturn(cocaProduct);

        Mockito.when(productRepositoryMock.existsById(Mockito.anyString())).thenReturn(false);
        Mockito.doAnswer(invocationOnMock -> {
            Product product = (Product) invocationOnMock.getArguments()[0];
            product.setId(test_id);
            return product;
        }).when(productRepositoryMock).createProduct(cocaProduct);

        Mockito.doAnswer(invocationOnMock -> {
            Product product = (Product) invocationOnMock.getArguments()[0];
            cocaProductDTO.setId(product.getId());
            return cocaProductDTO;
        }).when(productMapperMock).mapToProductDTO(Mockito.any(Product.class));


        ProductDTO productCreated = productService.createProduct(cocaProductDTO);

        assertTrue(productCreated.getId().contains(test_id));
        assertEquals(1,userTest.getProductsPublished().size());
    }

    @Test // Test de servicio para historia 2
    void testSuccessfulSubscriptionToAProduct() {
        String test_id = "1";
        String pastFrola = "Pasta frola";

        Product pastaFrolaProduct = new Product(pastFrola,"www.dns.a/a.img",5,2,33.2, 10.0, ProductUnit.KILOGRAM);

        Mockito.when(productRepositoryMock.update(Mockito.any(),Mockito.any(Product.class))).thenReturn(pastaFrolaProduct);
        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(pastaFrolaProduct);


        Mockito.when(userServiceMock.getLoggedUser()).thenReturn(userTest);

        productService.subscribeLoggedUser(test_id);

        assertEquals(pastaFrolaProduct.getSuscribers().size(),1);
        assertEquals(pastaFrolaProduct.getSuscribers().get(0).getName(),"Migue");
        assertEquals(userTest.getProductsSubscribed().get(0).getName(), pastFrola);
    }

    // Tests de servicio para historia 4
    @Test
    void testNotifyUsersWhenClosingAPublication() {

        Product beefProduct = new Product("Beef","www.dns.a/a.img",5,2,33.2, 10.0, ProductUnit.KILOGRAM);

        List<User> subscribers = new ArrayList<>();
        subscribers.add(new User("Ernesto",13,"ee@asd.com"));
        subscribers.add(new User("Guille",23,"gg@asd.com"));
        beefProduct.setSuscribers(subscribers);
        beefProduct.setOwner(userTest);
        userTest.setProductsPublished(List.of(beefProduct));

        Mockito.when(productRepositoryMock.update(Mockito.any(),Mockito.any(Product.class))).thenReturn(beefProduct);
        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(beefProduct);



        productService.closeProduct("1");

        assertEquals(1,subscribers.get(0).getNotifications().size());
        assertEquals(1,subscribers.get(1).getNotifications().size());
        assertEquals(ProductState.CLOSED_COMPLETED, beefProduct.getState());
    }

    @Test
    void testNotifyUsersWhenClosingAnIncompletePublication() {

        Product bikePackProduct = new Product("Bike x100","www.dns.a/a.img",5,2,33.2, 10.0, ProductUnit.KILOGRAM);

        List<User> subscribers = new ArrayList<>();
        subscribers.add(new User("Ernesto",13,"ee@asd.com"));
        bikePackProduct.setSuscribers(subscribers);
        bikePackProduct.setOwner(userTest);

        userTest.setProductsPublished(List.of(bikePackProduct));
        Mockito.when(productRepositoryMock.update(Mockito.any(),Mockito.any(Product.class))).thenReturn(bikePackProduct);
        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(bikePackProduct);

        productService.closeProduct("1");

        assertEquals(1,subscribers.get(0).getNotifications().size());
        assertEquals(ProductState.CLOSED_INCOMPLETE, bikePackProduct.getState());
    }

    @Test // Parte de la historia 6
    void testThrowExceptionWhenSubscribingOneMoreUserToAProduct() {

        String test_id = "1";
        String applePie = "Apple Pie";

        Product applePieProduct = new Product(applePie,"www.dns.a/a.img",2,1,33.2, 10.0, ProductUnit.KILOGRAM);

        applePieProduct.setSuscribers(List.of(
                Mockito.mock(User.class),
                Mockito.mock(User.class)
        ));

        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(applePieProduct);


        assertThrows(ProductFullException.class, () -> productService.subscribeLoggedUser("1"));
    }
}