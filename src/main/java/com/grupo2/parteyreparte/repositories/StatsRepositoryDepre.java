package com.grupo2.parteyreparte.repositories;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class StatsRepositoryDepre {

    private Set<String> uniqueUsersId = new HashSet<>();

    public void addInteraction(String id){
        uniqueUsersId.add(id);
    }

    public int uniqueUsers(){
        return uniqueUsersId.size();
    }
}
