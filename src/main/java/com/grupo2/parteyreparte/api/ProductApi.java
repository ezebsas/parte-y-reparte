package com.grupo2.parteyreparte.api;

import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.mappers.ApiResponse;
import com.grupo2.parteyreparte.models.Interaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Products")
public interface ProductApi {
    @Operation(summary = "Get all published products", description = "Get all published products")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAll();
    @Operation(summary = "Published a product", description = "Publish a product passed by body of the request")
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@RequestBody ProductDTO productDTO);
    @Operation(summary = "Get info of a product by ID", description = "Get all the information about the product ID")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable String id);
    @Operation(summary = "Edit all the product")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO);
    @Operation(summary = "Edit a part of the product")
    public ResponseEntity<ApiResponse<ProductDTO>> patchProduct(@PathVariable String id, @RequestBody ProductDTO productDTO);

    @Operation(summary = "Subscribe to a product")
    public ResponseEntity<ApiResponse<ProductDTO>> subscribeUser(@PathVariable String id);

    @Operation(summary = "Get all subscriber of a product")
    public ResponseEntity<ApiResponse<List<UserDTO>> > getParticipants(@PathVariable String id);

    @Operation(summary = "Close a product")
    public ResponseEntity<ApiResponse<ProductDTO>> closeProduct(@PathVariable String id);

}
