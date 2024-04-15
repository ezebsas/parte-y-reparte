package com.grupo2.parteyreparte.controllers;

import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.mappers.UserMapper;
import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        //updatear user
        return ResponseEntity.ok(userUpdate);
    }

    @GetMapping("/users/me/subscriptions")
    public ResponseEntity<List<Product>> getCurrentUserProducts(){
        return ResponseEntity.ok(userService.getLoggedUserProductsSubscribed());
    }

    @DeleteMapping("/users/me/subscriptions/{id}")
    public ResponseEntity<List<Product>> getCurrentUserProducts(@PathVariable("id") String productId){
        if(userService.deleteUserProductById(productId)){
            return ResponseEntity.ok(userService.getLoggedUserProductsSubscribed());
        }else
            return ResponseEntity.badRequest().build();

    }
}
