package com.grupo2.parteyreparte.controllers;

import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.exceptions.EntityNotFoundException;
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
    public ResponseEntity<UserDTO> getCurrentUser(){

        UserDTO user = userMapper.mapToUserDTO(userService.getLoggedUser());
        return ResponseEntity.ok(user);
    }

    @PatchMapping("users/me")
    public ResponseEntity<UserDTO> patchCurrentUser(@RequestBody UserDTO userUpdate){
        try {
            UserDTO updatedUser = userService.updateUser(userUpdate);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/users/me/subscriptions")
    public ResponseEntity<List<ProductDTO>> getCurrentUserProducts(){
        return ResponseEntity.ok(userService.getLoggedUserProductsSubscribedDTO());
    }

    @DeleteMapping("/users/me/subscriptions/{id}")
    public ResponseEntity<List<ProductDTO>> getCurrentUserProducts(@PathVariable("id") String productId){
        if(userService.deleteUserProductById(productId)){
            return ResponseEntity.ok(userService.getLoggedUserProductsSubscribedDTO());
        }else
            return ResponseEntity.badRequest().build();

    }

    @GetMapping("/users/me/notifications")
    public ResponseEntity<List<Notification>> getCurrentUserNotifications(){
        return ResponseEntity.ok(userService.getLoggedUserNotifications());
    }

    @GetMapping("/users/me/products")
    public ResponseEntity<List<ProductDTO>> getCurrentUserProductsPublished(){
        return ResponseEntity.ok(userService.getLoggedUserProductsPublished());
    }
}
