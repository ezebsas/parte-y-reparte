package com.grupo2.parteyreparte.mappers;

import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {

    private final UserMapper userMapper;

    @Autowired
    public ProductMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public ProductDTO mapToProductDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        List<UserDTO> suscribers = new ArrayList<UserDTO>();

        if (product.getSuscribers() != null) {
            for (User user : product.getSuscribers()) {
                suscribers.add(userMapper.mapToUserDTO(user));
            }
        }

        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setImage(product.getImage());
        productDTO.setLink(product.getLink());
        productDTO.setDeadline(product.getDeadline());
        productDTO.setMaxPeople(product.getMaxPeople());
        productDTO.setMinPeople(product.getMinPeople());
        productDTO.setTotalCost(product.getTotalCost());
        productDTO.setSubscribers(suscribers);
        productDTO.setState(product.getState());

        if (product.getOwner() != null) {
            productDTO.setOwner(userMapper.mapToUserDTO(product.getOwner()));
        }

        return productDTO;
    }

    public Product mapToProduct(ProductDTO productDTO){
        List<User> subscribers = new ArrayList<User>();
        if (productDTO.getSubscribers() != null) {
            for (UserDTO userDTO : productDTO.getSubscribers()) {
                subscribers.add(userMapper.mapToUser(userDTO));
            }
        }

        Product product = new Product(productDTO.getName(), productDTO.getImage(), productDTO.getMaxPeople(), productDTO.getMinPeople(), productDTO.getTotalCost());

        if (productDTO.getOwner() != null) {
            product.setOwner(userMapper.mapToUser(productDTO.getOwner()));
        }

        product.setId(productDTO.getId());
        product.setLink(productDTO.getLink());
        product.setDeadline(productDTO.getDeadline());
        product.setSuscribers(subscribers);
        product.setState(productDTO.getState());

        return product;
    }
}
