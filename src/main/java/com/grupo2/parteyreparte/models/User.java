package com.grupo2.parteyreparte.models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String id;
    private String name;
    private int age;
    private String email;
    private List<Product> productList;

    public User(String id, String name, int age, String email, List<Product> productList) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.productList = new ArrayList<Product>();
    }
}
