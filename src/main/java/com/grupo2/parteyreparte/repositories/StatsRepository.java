package com.grupo2.parteyreparte.repositories;

import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.ProductState;
import com.grupo2.parteyreparte.models.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class StatsRepository {

    private Set<Integer> uniqueUsersId = new HashSet<>();

    public void addInteraction(Integer id){
        uniqueUsersId.add(id);
    }

    public int uniqueUsers(){
        return uniqueUsersId.size();
    }
}
