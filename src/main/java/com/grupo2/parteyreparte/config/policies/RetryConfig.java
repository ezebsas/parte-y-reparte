package com.grupo2.parteyreparte.config.policies;

import dev.failsafe.RetryPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.OptimisticLockingFailureException;

import java.time.temporal.ChronoUnit;

@Configuration
public class RetryConfig {
    private static final int RetryDelayInMs = 100;
    private static final int MaximumRetryDelayInMs = 1000;
    private static final int MaxRetries = 4;

    @Bean
    public RetryPolicy<Object> optimisticLockRetry() {
        return  RetryPolicy.builder()
                .handle(OptimisticLockingFailureException.class)
                .withBackoff(RetryDelayInMs, MaximumRetryDelayInMs, ChronoUnit.MILLIS)
                .withMaxAttempts(MaxRetries)
                .build();
    }
}
