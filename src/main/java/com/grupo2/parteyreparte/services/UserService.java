package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    public String getLoggedUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();

    }

    public User getLoggedUser() {

        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    public List<Product> getLoggedUserProductsSubscribed() {

        return this.getLoggedUser().getProductsSubscribed();

    }

    public boolean deleteUserProductById(String idProduct) {

        List<Product> products = this.getLoggedUserProductsSubscribed();

        boolean hasProductId =!products.isEmpty() && products.stream().anyMatch(product -> product.getId().equals(idProduct));

        return hasProductId;

    }
}
