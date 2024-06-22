package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.models.Interaction;
import com.grupo2.parteyreparte.repositories.mongo.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StatsService {

    private final StatsRepository statsRepository;
    private final ProductService productService;

    @Autowired
    public StatsService(StatsRepository statsRepository, ProductService productService, UserService userService) {
        this.productService = productService;
        this.statsRepository = statsRepository;
    }

    public Integer getPublicationsCount(){

        return productService.countProducts();
    }

    public Integer getUniqueUsersCount(){
        Long uniqueUsersCount = statsRepository.countUniqueUsers();
        return uniqueUsersCount != null ? Math.toIntExact(uniqueUsersCount) : 0;
    }

    public Integer getAmountInteractions() {
        return Math.toIntExact(statsRepository.count());
    }

    public List<Interaction> getAll() {
        return statsRepository.findAll();
    }


    public void addInteraction(Interaction interaction) {
        statsRepository.save(interaction);

    }

    public void addInteractions(List<Interaction> interactions) {
        statsRepository.saveAll(interactions);
    }
}
