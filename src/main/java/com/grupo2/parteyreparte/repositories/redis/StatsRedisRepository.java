package com.grupo2.parteyreparte.repositories.redis;


import com.grupo2.parteyreparte.models.Interaction;
import com.grupo2.parteyreparte.repositories.mongo.ProductRepository;
import com.grupo2.parteyreparte.repositories.mongo.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StatsRedisRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public StatsRedisRepository(RedisTemplate<String, String> redisTemplate, UserRepository userRepository, ProductRepository productRepository) {
        this.redisTemplate = redisTemplate;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public void incrementCounter(Interaction.InteractionType interactionType) {
        redisTemplate.opsForValue().increment(interactionType.toString());
    }

    public Long getCounter(Interaction.InteractionType interactionType) {
        String count = redisTemplate.opsForValue().get(interactionType.toString());
        return count != null ? Long.parseLong(count) : 0L;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initCounters() {
        Integer productsCount = Math.toIntExact(productRepository.count());
        redisTemplate.opsForValue().set(Interaction.InteractionType.PRODUCT_CREATION.toString(), String.valueOf(productsCount));

        Integer usersCount = Math.toIntExact(userRepository.count());
        redisTemplate.opsForValue().set(Interaction.InteractionType.NEW_USER.toString(), String.valueOf(usersCount));
    }
}