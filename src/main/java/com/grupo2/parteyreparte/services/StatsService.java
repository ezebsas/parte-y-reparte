package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.models.Interaction;
import com.grupo2.parteyreparte.repositories.redis.StatsRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StatsService {
    private final ProductService productService;
    private final StatsRedisRepository statsRedisRepository;

    @Autowired
    public StatsService(ProductService productService, UserService userService, StatsRedisRepository statsRedisRepository) {
        this.productService = productService;
        this.statsRedisRepository = statsRedisRepository;
    }

    public Integer getPublicationsCount(){

        return productService.countProducts();
    }

    public Integer getUniqueUsersCount(){
        return Math.toIntExact(statsRedisRepository.getCounter(Interaction.InteractionType.NEW_USER));
    }

    public Integer getAmountProducts() {
        Long productsCount = statsRedisRepository.getCounter(Interaction.InteractionType.PRODUCT_CREATION);
        return productsCount != null ? Math.toIntExact(productsCount) : 0;
    }

    public void addInteraction(Interaction.InteractionType interactionType) {
        statsRedisRepository.incrementCounter(interactionType);
    }


}
