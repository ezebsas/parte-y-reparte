package com.grupo2.parteyreparte.controllers;

import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.mappers.ApiResponse;
import com.grupo2.parteyreparte.services.AuthService;
import com.grupo2.parteyreparte.services.StatsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/api/v1")
@RestController
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Stats")
public class StatsController {
    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }
    @GetMapping("/stats/publications")
    public ResponseEntity<ApiResponse<Integer>> numberOfPublications(){


        ApiResponse<Integer> response = new ApiResponse<>();
        response.setMessage("Amount of publications ");
        response.setValue(statsService.getPublicationsCount());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats/unique-users")
    public ResponseEntity<ApiResponse<Integer>> uniqueUsers(){

        ApiResponse<Integer> response = new ApiResponse<>();
        response.setMessage("Amount of unique users interactions ");
        response.setValue(statsService.getUniqueUsersCount());
        return ResponseEntity.ok(response);
    }
}
