package com.grupo2.parteyreparte.repositories;

import com.grupo2.parteyreparte.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {



}
