package com.grupo2.parteyreparte.controllers;

import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.exceptions.EntityNotFoundException;
import com.grupo2.parteyreparte.mappers.ApiResponse;
import com.grupo2.parteyreparte.mappers.UserMapper;
import com.grupo2.parteyreparte.models.Notification;
import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;
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
    public ResponseEntity <ApiResponse<List<Notification>> >  getCurrentUserNotifications(){

        ApiResponse<List<Notification>> response = new ApiResponse<>();
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
