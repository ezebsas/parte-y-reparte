package com.grupo2.parteyreparte.services;


import com.grupo2.parteyreparte.repositories.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    @Autowired
    private StatsRepository statsRepository;

    public Integer getPublicationsCount(){

        return statsRepository.findAllPublications().size();
    }

    public Integer getUniqueUsersCount(){

        return statsRepository.findAllUsers().size();
    }
}
