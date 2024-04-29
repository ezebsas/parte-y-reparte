package com.grupo2.parteyreparte.mappers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    private String message;
    private T value;
}
