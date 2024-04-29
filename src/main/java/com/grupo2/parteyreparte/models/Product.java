package com.grupo2.parteyreparte.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Product {

    @Getter
    private String id;
    private String name;
    private String image;
    private String link;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
    private int maxPeople;
    private int minPeople;
    private Double totalCost;
    private List<User> suscribers;
    private ProductState state;
    private User owner;

    public Product(String name, String image, int maxPeople, int minPeople, Double totalCost) {
        this.name = name;
        this.image = image;
        this.link = "";
        this.maxPeople = maxPeople;
        this.minPeople = minPeople;
        this.totalCost = totalCost;
        this.suscribers = new ArrayList<User>();
        this.state = ProductState.OPEN;
        this.createdAt = LocalDateTime.now();
    }

    public void partReceived() {
        //TODO
    }

    public boolean isFull() {
            return this.suscribers.size() >= this.maxPeople;
        }

    public void subscribeUser(User user) {
        this.suscribers.add(user);

        if (this.isFull()) {
            this.state = ProductState.CLOSED;
        }
    }

    public void close() {
        if (this.suscribers.size() < this.minPeople) {
            this.state = ProductState.INCOMPLETED;
        } else {
            this.state = ProductState.CLOSED;
        }

        this.closedAt = LocalDateTime.now();
        this.notifyUsers(this.state);
    }

    private void notifyUsers(ProductState state) {
        Notification notification = new Notification("Product closed", this.generateNotificationMessage(), LocalDateTime.now());
        this.suscribers.forEach(user -> user.notifyClosedProduct(notification));
    }

    private String generateNotificationMessage() {
        String action;
        String reason = "";
        String nextStep = "";

        switch (this.state) {
            case CLOSED:
                action = "closed";
                if (this.isFull()) {
                    reason = "because it reached the maximum number of subscribers";
                } else {
                    reason = "by the seller or because the deadline was reached";
                }
                nextStep = "You can proceed with the purchase";
                break;
            case INCOMPLETED:
                action = "closed";
                reason = "by the seller or because the deadline was reached";
                nextStep = "The product is not available for purchase because it did not reach the minimum number of subscribers";
                break;
            default:
                action = "unknown state";
                reason = "The current state of the product is unknown";
                nextStep = "Please contact customer support for assistance";
                break;
        }

        return String.format("The product %s has been %s %s. %s.", this.name, action, reason, nextStep);
    }


    public boolean isOwner(User user) {
        return this.owner.getId() == user.getId();
    }

}
