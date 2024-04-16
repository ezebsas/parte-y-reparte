package com.grupo2.parteyreparte.controllers;

import com.grupo2.parteyreparte.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class StatsController {
    @Autowired
    private StatsService statsService;
    @GetMapping("/stats/publications")
    public ResponseEntity<Integer> numberOfPublications(){


        return ResponseEntity.ok(statsService.getPublicationsCount());
    }

    @GetMapping("/stats/unique-users")
    public ResponseEntity<Integer> uniqueUsers(){


        return ResponseEntity.ok(statsService.getUniqueUsersCount());
    }
}
