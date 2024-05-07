package com.grupo2.parteyreparte.controllers;

import com.grupo2.parteyreparte.dtos.NotificationDTO;
import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.mappers.ApiResponse;
import com.grupo2.parteyreparte.mappers.UserMapper;
import com.grupo2.parteyreparte.models.Notification;
import com.grupo2.parteyreparte.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/api/v1")
@RestController
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "User")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/users/me")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser(){

        UserDTO user = userMapper.mapToUserDTO(userService.getLoggedUser());
        ApiResponse<UserDTO> response = new ApiResponse<>();
        response.setMessage("User's information");
        response.setValue(user);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("users/me")
    public ResponseEntity<ApiResponse<UserDTO>> patchCurrentUser(@RequestBody UserDTO userUpdate){

            UserDTO updatedUser = userService.updateUser(userUpdate);
            ApiResponse<UserDTO> response = new ApiResponse<>();
            response.setMessage("Successful user's information update");
            response.setValue(updatedUser);
            return ResponseEntity.ok(response);
    }


    @GetMapping("/users/me/subscriptions")
    public ResponseEntity<ApiResponse<List<ProductDTO>> > getCurrentUserProducts(){

        ApiResponse<List<ProductDTO>> response = new ApiResponse<>();
        response.setMessage("User's Subscriptions");
        response.setValue(userService.getLoggedUserProductsSubscribedDTO());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/me/subscriptions/{id}")
    public ResponseEntity< ApiResponse<List<ProductDTO>> > getCurrentUserProducts(@PathVariable("id") String productId){

        userService.deleteUserProductById(productId);
        ApiResponse<List<ProductDTO>> response = new ApiResponse<>();
        response.setMessage("User's subscriptions after deletion");
        response.setValue(userService.getLoggedUserProductsSubscribedDTO());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/me/notifications")
    public ResponseEntity <ApiResponse<List<NotificationDTO>> >  getCurrentUserNotifications(){

        ApiResponse<List<NotificationDTO>> response = new ApiResponse<>();
        response.setMessage("User's notifications");
        response.setValue(userService.getLoggedUserNotifications());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/me/products")
    public ResponseEntity<ApiResponse<List<ProductDTO>> > getCurrentUserProductsPublished(){

        ApiResponse<List<ProductDTO>> response = new ApiResponse<>();
        response.setMessage("User's published products");
        response.setValue(userService.getLoggedUserProductsPublished());
        return ResponseEntity.ok(response);
    }
}
