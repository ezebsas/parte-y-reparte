package com.grupo2.parteyreparte.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Product {

    @Getter
    private String id;
    private String name;
    private String image;
    private String link;
    private Date deadline;
    private int maxPeople;
    private int minPeople;
    private Double totalCost;
    private List<User> suscribers;
    private ProductState state;

    public Product(String name, String image, int maxPeople, int minPeople, Double totalCost) {
        this.name = name;
        this.image = image;
        this.link = "";
        this.maxPeople = maxPeople;
        this.minPeople = minPeople;
        this.totalCost = totalCost;
        this.suscribers = new ArrayList<User>();
        this.state = ProductState.OPEN;
    }

    public void partReceived() {
        //TODO
    }

    public boolean isFull() {
            return this.suscribers.size() >= this.maxPeople;
        }

    public void suscribeUser(User user) {
        this.suscribers.add(user);
    }

    public void close() {
        this.state = ProductState.CLOSED;
    }

    public void desuscribeUser(User user) {
        this.suscribers.remove(user);
    }
}
