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
        List<UserDTO> suscribers = new ArrayList<UserDTO>();
        for(User user : product.getSuscribers()){
            suscribers.add(userMapper.mapToUserDTO(user));
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setImage(product.getImage());
        productDTO.setLink(product.getLink());
        productDTO.setDeadline(product.getDeadline());
        productDTO.setMaxPeople(product.getMaxPeople());
        productDTO.setMinPeople(product.getMinPeople());
        productDTO.setTotalCost(product.getTotalCost());
        productDTO.setSuscribers(suscribers);
        productDTO.setState(product.getState());

        return productDTO;
    }
}
