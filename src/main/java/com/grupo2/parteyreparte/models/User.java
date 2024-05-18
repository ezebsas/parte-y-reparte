package com.grupo2.parteyreparte.models;

import com.grupo2.parteyreparte.dtos.UserDTO;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@Document
public class User implements UserDetails {

    @Id
    private String id;
    private String name;
    private int age;
    private String email;
    private String password;
    private List<Product> productsPublished;
    private List<Product> productsSubscribed;
    private List<Notification> notifications;


    public User(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.productsPublished = new ArrayList<Product>();
        this.productsSubscribed = new ArrayList<Product>();
        this.notifications = new ArrayList<Notification>();
    }

    public void addProductPublished(Product product) {
        this.productsPublished.add(product);
    }

    public void addProductSuscribed(Product product) {
        this.productsSubscribed.add(product);
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // TODO son todos users por ahora.
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void publishProduct(Product product) {
        this.productsPublished.add(product);
    }

    public void notifyClosedProduct(Notification notification) {
        this.notifications.add(notification);
    }

    public void susbribeProduct(Product product) {
        this.productsSubscribed.add(product);
    }

    public void updateUser(UserDTO userUpdate) {


        if (userUpdate.getName() != null) {
            this.setName(userUpdate.getName());
        }

        if ((Integer)userUpdate.getAge() != null) {
            this.setAge(userUpdate.getAge());
        }

        if (userUpdate.getEmail() != null) {
            this.setEmail(userUpdate.getEmail());
        }

    }

}
