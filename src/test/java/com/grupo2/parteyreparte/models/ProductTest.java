package com.grupo2.parteyreparte.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void productWithAQuantityOf2CannotBeDistributedTo3Users() {
        Product pepsi = new Product("Pepsi","www.dns.a/a.img",3,3,3000.0, 2.0, ProductUnit.UNIT);

        pepsi.subscribeUser(Mockito.mock(User.class));
        pepsi.subscribeUser(Mockito.mock(User.class));
        pepsi.subscribeUser(Mockito.mock(User.class));

        pepsi.close();

        assertEquals(ProductState.CANNOT_BE_DISTRIBUTED,pepsi.getState());
    }

    @Test
    void productCouldNotReachMinimumUsersWhenItClosed() {
        Product pepsi = new Product("Pepsi","www.dns.a/a.img",3,2,3000.0, 2.0, ProductUnit.UNIT);

        pepsi.subscribeUser(Mockito.mock(User.class));

        pepsi.close();

        assertEquals(ProductState.CLOSED_INCOMPLETE,pepsi.getState());
    }


    @Test
    void TestPartToPayFor3UsersIs500WhenTheFullPriceIs1500AndEachOneReceive2() {
        Product juice = new Product("Fruit Juice","www.dns.a/a.img",3,2,1500.0, 6.0, ProductUnit.UNIT);

        juice.subscribeUser(Mockito.mock(User.class));
        juice.subscribeUser(Mockito.mock(User.class));
        juice.subscribeUser(Mockito.mock(User.class));

        assertEquals(500,juice.partToPay());
        assertEquals(2,juice.partReceived());

    }

    @Test
    void nameIsUpdated() {
        Product juice = new Product("Fruit Juice","www.dns.a/a.img",3,2,1500.0, 6.0, ProductUnit.UNIT);
        Product juiceModified = new Product("Banana juice","www.dns.a/a.img",3,2,1500.0, 6.0, ProductUnit.UNIT);

        juice.patchProduct(juiceModified);

        assertEquals(juiceModified.getName(),juice.getName());
    }


}