package com.grupo2.parteyreparte.repositories.mongo;

import com.grupo2.parteyreparte.models.Interaction;
import lombok.Getter;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends MongoRepository<Interaction, String> {

    @Aggregation(pipeline = { "{$group: {_id: '$userId'}}", "{$count: 'count'}" })
    Long countUniqueUsers();

}
