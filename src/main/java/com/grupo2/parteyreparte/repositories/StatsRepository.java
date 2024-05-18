package com.grupo2.parteyreparte.repositories;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class StatsRepository {

    private Set<String> uniqueUsersId = new HashSet<>();

    public void addInteraction(String id){
        uniqueUsersId.add(id);
    }

    public int uniqueUsers(){
        return uniqueUsersId.size();
    }
}
