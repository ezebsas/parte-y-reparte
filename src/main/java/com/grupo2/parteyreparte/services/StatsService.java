package com.grupo2.parteyreparte.services;


import com.grupo2.parteyreparte.repositories.ProductRepository;
import com.grupo2.parteyreparte.repositories.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private StatsRepository statsRepository;
    private ProductRepository productRepository;
    private UserService userService;

    @Autowired
    public StatsService(StatsRepository statsRepository, ProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.statsRepository = statsRepository;
    }

    public Integer getPublicationsCount(){

        return productRepository.getAll().size();
    }

    public Integer getUniqueUsersCount(){

        return statsRepository.uniqueUsers();
    }


    public void addInteraction(){
        statsRepository.addInteraction(userService.getLoggedUserId());
    }
}
