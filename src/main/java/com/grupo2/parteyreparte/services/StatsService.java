package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.models.Interaction;
import com.grupo2.parteyreparte.repositories.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

        return productService.countProducts();
    }

    public Integer getUniqueUsersCount(){
        long uniqueUsersCount = statsRepository.countUniqueUsers();

        return Math.toIntExact(uniqueUsersCount);
    }


    public void addInteraction(Interaction.InteractionType interactionType) {
        String userId = userService.getLoggedUserId();

        Interaction interaction = new Interaction(userId, interactionType);
        statsRepository.save(interaction);

    }
}
