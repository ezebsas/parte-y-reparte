package com.grupo2.parteyreparte.repositories;

import com.grupo2.parteyreparte.models.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productList = new ArrayList<>();

    public ProductRepository() {
        productList.add(new Product("a","a",1,2,12.2));
    }

    public List<Product> getAll() {
        return this.productList;
    }
}
