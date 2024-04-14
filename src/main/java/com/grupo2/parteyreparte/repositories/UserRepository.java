package com.grupo2.parteyreparte.repositories;

import com.grupo2.parteyreparte.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    List<User> users = new ArrayList<>();

    public Optional<User> findByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }


    public void save(User user) {
        // TODO luego de implementar repositorios esto vuela y se genera solo el id
        user.setId(user.getEmail());
        this.users.add(user);
    }
}
