package com.grupo2.parteyreparte.repositories;

import com.grupo2.parteyreparte.models.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private List<Product> productList = new ArrayList<>();

    public ProductRepository() {
        Product product = new Product("a", "a", 1, 2, 12.2);
        product.setId("1");
        productList.add(product);
    }

    public List<Product> getAll() {
        return this.productList;
    }

    public Product createProduct(Product product) {
        this.productList.add(product);
        return product;
    }

    public boolean existsById(String id) {
        return this.productList.stream().anyMatch(product -> product.getId().equals(id));
    }

    public Optional<Product> findById(String id) {
        return this.productList.stream().filter(product -> product.getId().equals(id)).findFirst();
    }

    public Product update(String id, Product product) {
        Product oldProduct = this.findById(id).get();
        product.setId(id);
        this.productList.remove(oldProduct);
        this.productList.add(product);

        return product;
    }
}
