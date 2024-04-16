package com.grupo2.parteyreparte.repositories;

import com.grupo2.parteyreparte.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class UserRepository {

    List<User> users = new ArrayList<>();

    public Optional<User> findByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }


    public void save(User user) {

        // TODO futuramente se va a cambiar
        if (user.getId() == 0) {
            user.setId(new Random().nextInt(999999));
        }

        this.users.add(user);
    }
}
