package com.grupo2.parteyreparte.controllers;

import com.grupo2.parteyreparte.api.StatsApi;
import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.mappers.ApiResponse;
import com.grupo2.parteyreparte.models.Interaction;
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

@CrossOrigin(value = "*")
@RequestMapping("/api/v1")
@RestController
@SecurityRequirement(name = "BearerAuth")
public class StatsController implements StatsApi {
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

    @GetMapping("/stats/interactions-amount")
    public ResponseEntity<ApiResponse<Integer>> interactionsAmount(){

        ApiResponse<Integer> response = new ApiResponse<>();
        response.setMessage("Amount of interactions");
        response.setValue(statsService.getAmountInteractions());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats/interactions")
    public ResponseEntity<ApiResponse<List<Interaction>>> interactions(){

        ApiResponse<List<Interaction>> response = new ApiResponse<>();
        response.setMessage("All interactions");
        response.setValue(statsService.getAll());
        return ResponseEntity.ok(response);
    }
}
