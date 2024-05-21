package com.grupo2.parteyreparte.services;


import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.repositories.ProductRepository;
import com.grupo2.parteyreparte.repositories.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StatsService {

    private StatsRepository statsRepository;
    private ProductService productService;
    private UserService userService;

    @Autowired
    public StatsService(StatsRepository statsRepository, ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
        this.statsRepository = statsRepository;
    }

    public Integer getPublicationsCount(){

        return productService.getAll().size();
    }

    public Integer getUniqueUsersCount(){

        List<ProductDTO> productDTOList = productService.getAll();

        Set<String> uniqueUserThatInteract = new HashSet<>();

        productDTOList.forEach(productDTO -> {
            uniqueUserThatInteract.add(productDTO.getOwner().getEmail());
            uniqueUserThatInteract.addAll(productDTO.getSubscribers().stream().map(UserDTO::getEmail).toList());
        });

        return uniqueUserThatInteract.size();
    }


    public void addInteraction(){
        statsRepository.addInteraction(userService.getLoggedUserId());
    }
}
