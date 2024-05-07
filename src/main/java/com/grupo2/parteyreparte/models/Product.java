package com.grupo2.parteyreparte.models;

import com.grupo2.parteyreparte.dtos.ProductDTO;
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
    private ProductUnit unit;
    private Double quantity;

    public Product(String name, String image, int maxPeople, int minPeople, Double totalCost, Double quantity, ProductUnit unit) {
        this.name = name;
        this.image = image;
        this.link = "";
        this.maxPeople = maxPeople;
        this.minPeople = minPeople;
        this.totalCost = totalCost;
        this.suscribers = new ArrayList<User>();
        this.state = ProductState.OPEN;
        this.createdAt = LocalDateTime.now();
        this.quantity = quantity;
        this.unit = unit;
    }

    public Double partReceived() {
        return this.quantity / this.suscribers.size();
    }

    public Double partToPay() {
        return this.totalCost / this.suscribers.size();
    }

    public boolean isFull() {
        return this.suscribers.size() >= this.maxPeople;
    }

    public void subscribeUser(User user) {
        this.suscribers.add(user);

        if (this.isFull()) {
            this.state = ProductState.CLOSED_COMPLETED;
        }
    }

    public void close() {
        if (this.suscribers.size() < this.minPeople) {
            this.state = ProductState.CLOSED_INCOMPLETE;
        } else if (!this.canBeDistributed()) {
            this.state = ProductState.CANNOT_BE_DISTRIBUTED;
        } else {
            this.state = ProductState.CLOSED_COMPLETED;
        }

        this.closedAt = LocalDateTime.now();
        this.notifyUsers(this.state);
    }
    private boolean canBeDistributed() {
        return this.unit != ProductUnit.UNIT || this.quantity % this.suscribers.size() == 0;
    }

    private void notifyUsers(ProductState state) {
        Notification notification = new Notification("Product closed", LocalDateTime.now(), this);
        this.suscribers.forEach(user -> user.notifyClosedProduct(notification));
    }

    public boolean isOwner(User user) {
        return this.owner.getId() == user.getId();
    }

    public void patchProduct(Product productUpdate) {


        if (productUpdate.getName() != null) {
            this.setName(productUpdate.getName());
        }

        if (productUpdate.getImage() != null) {
            this.setImage(productUpdate.getImage());
        }

        if (productUpdate.getLink() != null) {
            this.setLink(productUpdate.getLink());
        }

        if (productUpdate.getDeadline() != null) {
            this.setDeadline(productUpdate.getDeadline());
        }

        if (productUpdate.getMaxPeople() != 0) {
            this.setMaxPeople(productUpdate.getMaxPeople());
        }

        if (productUpdate.getMinPeople() != 0) {
            this.setMinPeople(productUpdate.getMinPeople());
        }

        Double totalCost = productUpdate.getTotalCost();
        if (totalCost != null) {
            this.setTotalCost(totalCost);
        }


        if (productUpdate.getSuscribers() != null) {
            this.setSuscribers(productUpdate.getSuscribers());
        }

        if (productUpdate.getState() != null) {
            this.setState(productUpdate.getState());
        }

        if (productUpdate.getUnit() != null) {
            this.setUnit(productUpdate.getUnit());
        }

        if (productUpdate.getQuantity() != null) {
            this.setQuantity(productUpdate.getQuantity());
        }

    }

}
