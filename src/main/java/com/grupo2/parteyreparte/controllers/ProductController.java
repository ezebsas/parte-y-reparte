package com.grupo2.parteyreparte.controllers;

import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.exceptions.EntityNotFoundException;
import com.grupo2.parteyreparte.mappers.ApiResponse;
import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.repositories.StatsRepository;
import com.grupo2.parteyreparte.services.ProductService;
import com.grupo2.parteyreparte.services.StatsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Products")
public class ProductController {

    private final ProductService productService;
    private final StatsService statsService;

    @Autowired
    public ProductController(ProductService productService, StatsService statsService) {
        this.productService = productService;
        this.statsService = statsService;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>> > getAll() {

        List<ProductDTO> products = productService.getAll();
        ApiResponse<List<ProductDTO>> response = new ApiResponse<>();
        response.setMessage("All products");
        response.setValue(products);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@RequestBody ProductDTO productDTO) {

            ProductDTO createdProduct = productService.createProduct(productDTO);
            statsService.addInteraction();
            ApiResponse<ProductDTO> response = new ApiResponse<>();
            response.setMessage("New product successfully created ");
            response.setValue(createdProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable String id) {

            ProductDTO product = productService.getProductDTOById(id);
            ApiResponse<ProductDTO> response = new ApiResponse<>();
            response.setMessage("Retrieved product: " + id);
            response.setValue(product);
            return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) {

            ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
            ApiResponse<ProductDTO> response = new ApiResponse<>();
            response.setMessage("Updated product: " + id);
            response.setValue(updatedProduct);
            return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> patchProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) {

            ProductDTO updatedProduct = productService.patchProduct(id, productDTO);
            ApiResponse<ProductDTO> response = new ApiResponse<>();
            response.setMessage("Updated product: " + id);
            response.setValue(updatedProduct);
            return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/subscription")
    public ResponseEntity<ApiResponse<ProductDTO>> subscribeUser(@PathVariable String id) {

            ProductDTO product = productService.subscribeLoggedUser(id);
            statsService.addInteraction();
            ApiResponse<ProductDTO> response = new ApiResponse<>();
            response.setMessage("Subscribed to product: " + id);
            response.setValue(product);
            return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<ApiResponse<List<UserDTO>> > getParticipants(@PathVariable String id) {

            List<UserDTO> participants = productService.getParticipants(id);
            ApiResponse<List<UserDTO>> response = new ApiResponse<>();
            response.setMessage("Product's subscribers ");
            response.setValue(participants);
            return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<ApiResponse<ProductDTO>> closeProduct(@PathVariable String id) {

            ProductDTO product = productService.closeProduct(id);
            ApiResponse<ProductDTO> response = new ApiResponse<>();
            response.setMessage("Closed product ");
            response.setValue(product);
            return ResponseEntity.ok(response);

    }


}
