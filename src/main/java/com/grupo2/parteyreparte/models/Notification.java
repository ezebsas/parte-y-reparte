package com.grupo2.parteyreparte.models;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "Notifications")
public class Notification {
    @Id
    private String id;
    private String title;
    private String message;
    private LocalDateTime date;
    private Product product;

    public Notification(String title, LocalDateTime date, Product product) {
        this.title = title;
        this.date = date;
        this.product = product;
        this.message = this.generateProductNotificationMessage();
    }

    private String generateProductNotificationMessage() {
        String action;
        String reason = "";
        String nextStep = "";

        switch (product.getState()) {
            case CLOSED_COMPLETED:
                action = "closed";
                if (product.isFull()) {
                    reason = "because it reached the maximum number of subscribers";
                } else {
                    reason = "by the seller or because the deadline was reached";
                }
                nextStep = "Congratulations, you can proceed with the purchase. The final price per subscriber is $" + product.partToPay() + ", and you will receive " + product.partReceived() + " " + product.getUnit();
                break;
            case CLOSED_INCOMPLETE:
                action = "closed";
                reason = "by the seller or because the deadline was reached";
                nextStep = "The product is not available for purchase because it did not reach the minimum number of subscribers";
                break;
            case CANNOT_BE_DISTRIBUTED:
                action = "closed";
                reason = "by the seller or because the deadline was reached";
                nextStep = "The product is not available for purchase because it cannot be distributed evenly among the subscribers";
                break;
            default:
                action = "unknown state";
                reason = "The current state of the product is unknown";
                nextStep = "Please contact customer support for assistance";
                break;
        }

        return String.format("The product %s has been %s %s. %s.", product.getName(), action, reason, nextStep);
    }
}
