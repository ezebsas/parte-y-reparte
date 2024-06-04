package com.grupo2.parteyreparte.api;

import com.grupo2.parteyreparte.dtos.NotificationDTO;
import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.mappers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User")
public interface UserApi {
    @Operation(summary = "Get the logged user data", description = "Get the data of the user passed in the JWT")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser();

    @Operation(summary = "Edit user info", description = "Send data to edit the current user info")
    public ResponseEntity<ApiResponse<UserDTO>> patchCurrentUser(@RequestBody UserDTO userUpdate);

    @Operation(summary = "Get subscribed products", description = "Get all subscribed products that did the user")
    public ResponseEntity<ApiResponse<List<ProductDTO>> > getCurrentUserProducts();

    @Operation(summary = "Unsubscribe a product", description = "Unsubscribe the product passed by path param")
    public ResponseEntity< ApiResponse<List<ProductDTO>> > getCurrentUserProducts(@PathVariable("id") String productId);

    @Operation(summary = "Get all my notifications", description = "Get a list of my notifications")
    public ResponseEntity <ApiResponse<List<NotificationDTO>> >  getCurrentUserNotifications();

    @Operation(summary = "Get all my published products", description = "Get a list of published products")
    public ResponseEntity<ApiResponse<List<ProductDTO>> > getCurrentUserProductsPublished();
}
