package com.grupo2.parteyreparte.repositories.redis;


import com.grupo2.parteyreparte.models.Interaction;
import org.springframework.data.repository.CrudRepository;

public interface InteractionRepository extends CrudRepository<Interaction, String> {
}

