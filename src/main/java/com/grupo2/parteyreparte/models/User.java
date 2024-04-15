package com.grupo2.parteyreparte.models;

import lombok.*;
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
public class User implements UserDetails {

    private String id;
    private String name;
    private int age;
    private String email;
    private String password;
    private List<Product> productsPublished;
    private List<Product> productsSubscribed;
    private List<Notification> notifications;


    public User(String id, String name, int age, String email, List<Product> productsPublished, List<Product> productsSubscribed, List<Notification> notifications) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.productsPublished = new ArrayList<Product>();
        this.productsSubscribed = new ArrayList<Product>();
        this.notifications = new ArrayList<Notification>();
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

    public void notifyClosedProduct(Notification notification) {
        this.notifications.add(notification);
    }
}
