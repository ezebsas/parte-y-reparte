package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.dtos.NotificationDTO;
import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.mappers.NotificationMapper;
import com.grupo2.parteyreparte.mappers.ProductMapper;
import com.grupo2.parteyreparte.mappers.UserMapper;
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
    private final UserMapper userMapper;
    private final NotificationMapper notificationMapper;

    @Autowired
    public UserService(ProductMapper productMapper, UserMapper userMapper, NotificationMapper notificationMapper) {
        this.productMapper = productMapper;
        this.userMapper = userMapper;
        this.notificationMapper = notificationMapper;
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

    public List<NotificationDTO> getLoggedUserNotifications() {
        return (this.getLoggedUser().getNotifications().stream().map(notificationMapper::mapToNotificationDTO).collect(Collectors.toList()));
    }

    public List<ProductDTO> getLoggedUserProductsPublished() {
        return this.getLoggedUser().getProductsPublished().stream().map(productMapper::mapToProductDTO).collect(Collectors.toList());
    }

    public UserDTO updateUser(UserDTO userUpdate) {

        User user = this.getLoggedUser();
        user.updateUser(userUpdate);
        return userMapper.mapToUserDTO(user);
    }
}
