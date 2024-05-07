package com.grupo2.parteyreparte.dtos;

import lombok.Data;
import lombok.Getter;

@Data
public class NotificationDTO {
    @Getter
    private String title;
    private String message;
    private String date;
    private String productId;

    public NotificationDTO(String title, String message, String date, String productId) {
        this.title = title;
        this.message = message;
        this.date = date;
        this.productId = productId;
    }
}
