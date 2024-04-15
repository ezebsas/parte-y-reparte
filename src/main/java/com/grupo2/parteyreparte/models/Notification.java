package com.grupo2.parteyreparte.models;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Notification {
    private String title;
    private String message;
    private LocalDate date;

    public Notification(String title, String message, LocalDate date) {
        this.title = title;
        this.message = message;
        this.date = date;
    }
}
