package com.grupo2.parteyreparte.services;


import com.grupo2.parteyreparte.repositories.ProductRepository;
import com.grupo2.parteyreparte.repositories.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

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
