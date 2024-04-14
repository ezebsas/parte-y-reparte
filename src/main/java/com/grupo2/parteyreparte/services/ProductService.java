package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {

        return productRepository.getAll();
    }
}
