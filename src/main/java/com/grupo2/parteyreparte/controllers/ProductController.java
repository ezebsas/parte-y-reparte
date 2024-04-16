package com.grupo2.parteyreparte.controllers;

import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.exceptions.EntityNotFoundException;
import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.repositories.StatsRepository;
import com.grupo2.parteyreparte.services.ProductService;
import com.grupo2.parteyreparte.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    private StatsService statsService;

    @GetMapping("")
    public ResponseEntity<List<ProductDTO>> getAll() {

        List<ProductDTO> products = productService.getAll();

        return ResponseEntity.ok(products);
    }

    @PostMapping("")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product) {
        try {
            ProductDTO createdProduct = productService.createProduct(product);
            statsService.addInteraction();
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String id) {
        try {
            ProductDTO product = productService.getProductDTOById(id);
            return ResponseEntity.ok(product);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String id, @RequestBody Product product) {
        try {
            ProductDTO updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> patchProduct(@PathVariable String id, @RequestBody Product product) {
        try {
            ProductDTO updatedProduct = productService.patchProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/subscription")
    public ResponseEntity<ProductDTO> subscribeUser(@PathVariable String id) {
        try {
            ProductDTO product = productService.subscribeLoggedUser(id);
            statsService.addInteraction();
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<UserDTO>> getParticipants(@PathVariable String id) {
        try {
            List<UserDTO> participants = productService.getParticipants(id);
            return ResponseEntity.ok(participants);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<ProductDTO> closeProduct(@PathVariable String id) {
        try {
            ProductDTO product = productService.closeProduct(id);
            return ResponseEntity.ok(product);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
