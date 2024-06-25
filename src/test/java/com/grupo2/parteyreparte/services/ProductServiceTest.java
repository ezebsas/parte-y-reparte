package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.exceptions.ProductClosedException;
import com.grupo2.parteyreparte.exceptions.ProductFullException;
import com.grupo2.parteyreparte.mappers.ProductMapper;
import com.grupo2.parteyreparte.models.*;
import com.grupo2.parteyreparte.repositories.mongo.NotificationRepository;
import com.grupo2.parteyreparte.repositories.mongo.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
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

    @MockBean
    ProductMapper productMapperMock;

    @MockBean
    NotificationRepository notificationRepositoryMock;

    User userTest;

    @BeforeEach
    public void init() {

        userTest = new User("Migue",3,"fed@asd.com");

        Mockito.when(userServiceMock.getLoggedUser()).thenReturn(userTest);
    }

    @Test // Test de servicio para historia 1
    void productCreatedSuccessfully() {

        String test_id = "1";
        Product cocaProduct = new Product("Coca","www.dns.a/a.img",3,2,33.2, 10.0, ProductUnit.UNIT);
        cocaProduct.setDeadline(LocalDateTime.now().plusDays(2));
        ProductDTO cocaProductDTO = new ProductDTO("Coca","www.dns.a/a.img",3,2,33.2, 10, ProductUnit.UNIT);

        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.of(cocaProduct));
        Mockito.when(productRepositoryMock.insert(Mockito.any(Product.class))).thenReturn(cocaProduct);

        Mockito.when(productMapperMock.mapToProduct(cocaProductDTO)).thenReturn(cocaProduct);

        Mockito.doAnswer(invocationOnMock -> {
            Product product = (Product) invocationOnMock.getArguments()[0];
            product.setId(test_id);
            return product;
        }).when(productRepositoryMock).insert(cocaProduct);

        Mockito.doAnswer(invocationOnMock -> {
            Product product = (Product) invocationOnMock.getArguments()[0];
            cocaProductDTO.setId(product.getId());
            return cocaProductDTO;
        }).when(productMapperMock).mapToProductDTO(Mockito.any(Product.class));


        ProductDTO productCreated = productService.createProduct(cocaProductDTO);

        Mockito.verify(productRepositoryMock).insert(cocaProduct);
        assertTrue(productCreated.getId().contains(test_id));
        Mockito.verify(userServiceMock).publishProduct(cocaProduct);
    }

    @Test // Test de servicio para historia 2
    void theSubscriptionToAProductWasSuccessfull() {
        String test_id = "1";
        String pastFrola = "Pasta frola";

        Product pastaFrolaProduct = new Product(pastFrola,"www.dns.a/a.img",5,2,33.2, 10.0, ProductUnit.KILOGRAM);

        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.of(pastaFrolaProduct));

        productService.subscribeLoggedUser(test_id);

        assertEquals(pastaFrolaProduct.getSuscribers().size(),1);
        assertEquals(pastaFrolaProduct.getSuscribers().get(0).getName(),"Migue");
        Mockito.verify(userServiceMock,Mockito.times(1)).subscribeToProduct(pastaFrolaProduct);
    }

    @Test
    void userCantUnsubscribeBecauseTheProductIsClosed() {

        User ernesto = new User("Ernesto",13,"ee@asd.com");
        String manaos = "Manaos";
        Product manaosProduct = new Product(manaos,"www.dns.a/a.img",5,2,33.2, 10.0, ProductUnit.KILOGRAM);
        userTest.setId("1");
        manaosProduct.setId("1");
        manaosProduct.setOwner(ernesto);

        List<User> subscribers = new ArrayList<>();
        subscribers.add(userTest);
        manaosProduct.setSuscribers(subscribers);
        manaosProduct.close();


        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.of(manaosProduct));

        assertThrows(ProductClosedException.class, () -> productService.unsubscribeLoggedUser("1"));
    }

    @Test
    void userCanUnsubscribeFromAProduct() {

        User ernesto = new User("Ernesto",13,"ee@asd.com");
        String manaos = "Manaos";
        Product manaosProduct = new Product(manaos,"www.dns.a/a.img",5,2,33.2, 10.0, ProductUnit.KILOGRAM);
        userTest.setId("1");
        manaosProduct.setId("1");
        manaosProduct.setOwner(ernesto);

        List<User> subscribers = new ArrayList<>();
        subscribers.add(userTest);
        manaosProduct.setSuscribers(subscribers);

        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.of(manaosProduct));


        productService.unsubscribeLoggedUser("1");
        Mockito.verify(userServiceMock,Mockito.times(1)).deleteUserProductById("1");
    }

    @Test
    void productCanBeUpdated() {
        ProductDTO cbseDTO = Mockito.mock(ProductDTO.class);
        Product cbseProduct = Mockito.mock(Product.class);

        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.of(cbseProduct));
        Mockito.when(productRepositoryMock.save(Mockito.any(Product.class))).thenReturn(cbseProduct);
        Mockito.when(productMapperMock.mapToProduct(cbseDTO)).thenReturn(cbseProduct);

        productService.updateProduct("1",cbseDTO);

        Mockito.verify(productRepositoryMock).save(Mockito.any(Product.class));
    }

    // Tests de servicio para historia 4
    @Test
    void notifyUsersOfTheClosureOfAPostThatSuccessfullyReachedTheMinimum() {

        Product beefProduct = new Product("Beef","www.dns.a/a.img",5,2,33.2, 10.0, ProductUnit.KILOGRAM);

        List<User> subscribers = new ArrayList<>();
        subscribers.add(new User("Ernesto",13,"ee@asd.com"));
        subscribers.add(new User("Guille",23,"gg@asd.com"));
        beefProduct.setSuscribers(subscribers);
        userTest.setId("1");
        beefProduct.setId("1");
        beefProduct.setOwner(userTest);
        userTest.setProductsPublished(List.of(beefProduct));

        Mockito.when(productRepositoryMock.save(Mockito.any(Product.class))).thenReturn(beefProduct);
        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.of(beefProduct));
        Mockito.doNothing().when(userServiceMock).notifyUser(Mockito.any(User.class),Mockito.any(Notification.class));


        productService.closeProduct("1");

        Mockito.verify(userServiceMock,Mockito.times(2)).notifyUser(Mockito.any(User.class),Mockito.any(Notification.class));
        assertEquals(ProductState.CLOSED_COMPLETED, beefProduct.getState());
    }

    @Test
    void notifyUsersWhenClosingAnIncompletePublication() {

        Product bikePackProduct = new Product("Bike x100","www.dns.a/a.img",5,3,33.2, 10.0, ProductUnit.KILOGRAM);
        userTest.setId("1");

        List<User> subscribers = new ArrayList<>();
        subscribers.add(new User("Ernesto",13,"ee@asd.com"));
        subscribers.add(new User("Gullermito",18,"g@g.com"));
        bikePackProduct.setSuscribers(subscribers);
        bikePackProduct.setOwner(userTest);

        userTest.setProductsPublished(List.of(bikePackProduct));
        Mockito.when(productRepositoryMock.save(Mockito.any(Product.class))).thenReturn(bikePackProduct);
        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.of(bikePackProduct));
        Mockito.when(notificationRepositoryMock.save(Mockito.any(Notification.class))).thenReturn(Mockito.mock(Notification.class));

        productService.closeProduct("1");

        assertEquals(ProductState.CLOSED_INCOMPLETE, bikePackProduct.getState());
        Mockito.verify(userServiceMock,Mockito.times(2)).notifyUser(Mockito.any(User.class),Mockito.any(Notification.class));
    }

    @Test // Parte de la historia 6
    void throwExceptionWhenSubscribingOneMoreUserToAProduct() {

        String test_id = "1";
        String applePie = "Apple Pie";

        Product applePieProduct = new Product(applePie,"www.dns.a/a.img",2,1,33.2, 10.0, ProductUnit.KILOGRAM);

        applePieProduct.setSuscribers(List.of(
                Mockito.mock(User.class),
                Mockito.mock(User.class)
        ));

        Mockito.when(productRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.of(applePieProduct));


        assertThrows(ProductFullException.class, () -> productService.subscribeLoggedUser("1"));
    }
}