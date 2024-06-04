package com.grupo2.parteyreparte.exceptions;

import com.grupo2.parteyreparte.dtos.ErrorResponseDTO;
import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.mappers.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ApiResponse<String>> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException){
        ApiResponse<String> response = new ApiResponse<>();
        response.setMessage("Error");
        response.setValue(entityNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ApiResponse<ErrorResponseDTO>> handleAuthException(AuthException e) {
        ApiResponse<ErrorResponseDTO> response = new ApiResponse<>();
        response.setMessage("Error");
        response.setValue(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ApiResponse<String>> handleProductFullException(ProductFullException e){
        ApiResponse<String> response = new ApiResponse<>();
        response.setMessage("Error");
        response.setValue(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ApiResponse<String>> handleProductClosedException(ProductClosedException e){
        ApiResponse<String> response = new ApiResponse<>();
        response.setMessage("Error");
        response.setValue(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ApiResponse<String>> handleUnauthorizedOperationException(UnauthorizedOperationException e){
        ApiResponse<String> response = new ApiResponse<>();
        response.setMessage("Error");
        response.setValue(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException e) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setMessage("Error");
        response.setValue(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setMessage("Error");
        response.setValue(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
