package com.grupo2.parteyreparte.repositories;

import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.ProductState;
import com.grupo2.parteyreparte.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class StatsRepository {

    protected List<Product> products = new ArrayList<>();
    protected List<User> users = new ArrayList<>();


    public List<Product> findAllPublications(){
        products.add(new Product("Prod 1", "a", 5, 3, 1000.0));
        products.add(new Product("Prod 2", "b", 5, 3, 112400.0));

        return products;
    }

    public List<User> findAllUsers(){
        users.add(new User("U 1", "a", 5, "as", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()) );
        users.add(new User("U 2", "a", 5, "as", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()) );

        return users;
    }
}
