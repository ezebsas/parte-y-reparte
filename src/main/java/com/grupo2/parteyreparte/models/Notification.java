package com.grupo2.parteyreparte.models;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Notification {
    private String title;
    private String message;
    private LocalDateTime date;

    public Notification(String title, String message, LocalDateTime date) {
        this.title = title;
        this.message = message;
        this.date = date;
    }
}
