package com.grupo2.parteyreparte.api;

import com.grupo2.parteyreparte.mappers.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
@Tag(name = "Stats")
public interface StatsApi {
    @Operation(summary = "Get a number of publications", description = "Get all amount of publications")
    public ResponseEntity<ApiResponse<Integer>> numberOfPublications();

    @Operation(summary = "Get a number of unique users", description = "Get all users that made their first interaction (Published a product or subscribed)")
    public ResponseEntity<ApiResponse<Integer>> uniqueUsers();
}
