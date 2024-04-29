package com.grupo2.parteyreparte.exceptions;

import com.grupo2.parteyreparte.dtos.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entityNotFoundException.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleAuthException(AuthException e) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleProductFullException(ProductFullException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleUnauthorizedOperationException(UnauthorizedOperationException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
