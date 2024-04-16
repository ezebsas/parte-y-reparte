package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.mappers.ProductMapper;
import com.grupo2.parteyreparte.models.Notification;
import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {
    private final ProductMapper productMapper;

    @Autowired
    public UserService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public int getLoggedUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    public User getLoggedUser() {

        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    public List<Product> getLoggedUserProductsSubscribed() {

        return this.getLoggedUser().getProductsSubscribed();

    }

    public List<ProductDTO> getLoggedUserProductsSubscribedDTO() {

        return this.getLoggedUser().getProductsSubscribed().stream().map(this.productMapper::mapToProductDTO).collect(Collectors.toList());

    }

    public boolean deleteUserProductById(String idProduct) {

        List<Product> products = this.getLoggedUserProductsSubscribed();

        boolean hasProductId =!products.isEmpty() && products.stream().anyMatch(product -> product.getId().equals(idProduct));

        return hasProductId;

    }

    public List<Notification> getLoggedUserNotifications() {
        return this.getLoggedUser().getNotifications();
    }

    public List<ProductDTO> getLoggedUserProductsPublished() {
        return this.getLoggedUser().getProductsPublished().stream().map(productMapper::mapToProductDTO).collect(Collectors.toList());
    }
}
