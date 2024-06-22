package com.grupo2.parteyreparte.repositories.mongo;

import com.grupo2.parteyreparte.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByNameEquals(String name);

    Optional<User> findByEmailEquals(String name);

/*
    List<User> users = new ArrayList<>();




    public void save(User user) {

        // TODO futuramente se va a cambiar
        if (user.getId() == 0) {
            user.setId(new Random().nextInt(999999));
        }

        this.users.add(user);
    }
    */

}
