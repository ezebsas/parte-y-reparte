package com.grupo2.parteyreparte.controllers;

import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getAll() {

        List<Product> products = productService.getAll();

        return ResponseEntity.ok(products);
    }

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> patchProduct(@PathVariable String id, @RequestBody Product product) {
        Product updatedProduct = productService.patchProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @PostMapping("/{id}/subscription")
    public ResponseEntity<Product> subscribeUser(@PathVariable String id, @RequestBody String userId) {
        Product product = productService.subscribeUser(id, userId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<User>> getParticipants(@PathVariable String id) {
        List<User> participants = productService.getParticipants(id);
        return ResponseEntity.ok(participants);
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<Product> closeProduct(@PathVariable String id) {
        Product product = productService.closeProduct(id);
        return ResponseEntity.ok(product);
    }


}
