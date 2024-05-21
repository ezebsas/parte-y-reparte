package com.grupo2.parteyreparte.repositories;

import com.grupo2.parteyreparte.models.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {



}
