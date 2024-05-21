package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.dtos.NotificationDTO;
import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.exceptions.EntityNotFoundException;
import com.grupo2.parteyreparte.mappers.NotificationMapper;
import com.grupo2.parteyreparte.mappers.ProductMapper;
import com.grupo2.parteyreparte.mappers.UserMapper;
import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.repositories.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public UserService(ProductMapper productMapper, UserMapper userMapper, NotificationMapper notificationMapper, UserRepository userRepository) {
        this.productMapper = productMapper;
        this.userMapper = userMapper;
        this.notificationMapper = notificationMapper;
        this.userRepository = userRepository;
    }

    public String getLoggedUserId() {
        String id = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return id;
    }

    public User getLoggedUser() {

        return this.userRepository.findById(this.getLoggedUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

    }

    public  void publishProduct(Product product){
        User user = this.getLoggedUser();
        user.publishProduct(product);
        this.userRepository.save(user);
    }
    public  void subscribeToProduct(Product product){
        User user = this.getLoggedUser();
        user.susbribeProduct(product);
        this.userRepository.save(user);
    }

    public List<Product> getLoggedUserProductsSubscribed() {

        return this.getLoggedUser().getProductsSubscribed();

    }

    public List<ProductDTO> getLoggedUserProductsSubscribedDTO() {

        return this.getLoggedUser().getProductsSubscribed().stream().map(this.productMapper::mapToProductDTO).collect(Collectors.toList());

    }

    public void deleteUserProductById(String idProduct) {

        List<Product> products = this.getLoggedUserProductsSubscribed();
        User user = this.getLoggedUser();

        boolean hasProductId =!products.isEmpty() && products.stream().anyMatch(product -> product.getId().equals(idProduct));

        if(!hasProductId){
            throw new EntityNotFoundException("Subscription not found");
        }

        user.setProductsSubscribed(products.stream().filter(product -> !product.getId().equals(idProduct)).toList() );
        this.userRepository.save(user);

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
        this.userRepository.save(user);
        return userMapper.mapToUserDTO(user);
    }
}
