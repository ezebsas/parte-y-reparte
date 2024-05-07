package com.grupo2.parteyreparte.repositories;

import com.grupo2.parteyreparte.exceptions.EntityNotFoundException;
import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.ProductUnit;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private static final String PRODUCT_WITH_ID = "Product with id = ";
    private static final String ALREADY_EXISTS = " already exists";
    private static final String DOES_NOT_EXIST = " does not exist";
    private List<Product> productList = new ArrayList<>();

    public ProductRepository() {
        Product product = new Product("a", "https://www.finedininglovers.com/es/sites/g/files/xknfdk1706/files/2022-10/tipos-de-manzanas%C2%A9iStock.jpg", 1, 2, 12.2, 10.0, ProductUnit.KILOGRAM);
        product.setId("1");
        productList.add(product);
    }

    public List<Product> getAll() {
        return this.productList;
    }

    public Product createProduct(Product product) {
        boolean exists = product.getId() != null && this.existsById(product.getId());

        if (exists) {
            throw new EntityExistsException(PRODUCT_WITH_ID + product.getId() + ALREADY_EXISTS);
        }

        product.setId(String.valueOf(this.productList.size() + 1));
        this.productList.add(product);
        return product;
    }

    public boolean existsById(String id) {
        return this.productList.stream().anyMatch(product -> product.getId().equals(id));
    }

    public Product findById(String id) {
        return this.productList.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_WITH_ID + id + DOES_NOT_EXIST));
    }

    public Product update(String id, Product product) {
        Product oldProduct = this.findById(id);

        product.setId(id);
        product.setOwner(oldProduct.getOwner());
        this.productList.remove(oldProduct);
        this.productList.add(product);

        return product;
    }
}
