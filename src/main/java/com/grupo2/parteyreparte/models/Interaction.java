package com.grupo2.parteyreparte.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter

@Document(collection = "Interactions")
public class Interaction {
    @Id
    private String id;
    private String userId;
    public enum InteractionType {
        PRODUCT_CREATION,
        PRODUCT_SUBSCRIPTION
    }

    private InteractionType interactionType;
    public Interaction(String userId, InteractionType interactionType) {
        this.userId = userId;
        this.interactionType = interactionType;
    }
}
