package com.grupo2.parteyreparte.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter

@RedisHash("Interaction")
@Document(collection = "Interactions")
public class Interaction {
    @Id
    private String id;
    private String userId;
    public enum InteractionType {
        PRODUCT_CREATION,
        NEW_USER
    }

    private LocalDateTime timestamp;

    private InteractionType interactionType;
    public Interaction(String userId, InteractionType interactionType) {
        this.userId = userId;
        this.interactionType = interactionType;
        this.timestamp = LocalDateTime.now();
    }
}
