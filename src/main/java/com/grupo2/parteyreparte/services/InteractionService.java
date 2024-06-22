package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.models.Interaction;
import com.grupo2.parteyreparte.repositories.redis.InteractionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InteractionService {

    private final InteractionRepository interactionRepository;
    private final StatsService statsService;
    private final UserService userService;

    public InteractionService(InteractionRepository interactionRepository, StatsService statsService, UserService userService) {
        this.interactionRepository = interactionRepository;
        this.statsService = statsService;
        this.userService = userService;
    }

    public void addInteraction(Interaction.InteractionType interactionType) {
        String userId = userService.getLoggedUserId();
        Interaction interaction = new Interaction(userId, interactionType);
        interactionRepository.save(interaction);

    }

    @Scheduled(fixedRate = 120000)
    public void transferInteractionsToMongoDB() {
        Iterable<Interaction> interactions = interactionRepository.findAll();
        List<Interaction> interactionList = new ArrayList<>();
        interactions.forEach(interactionList::add);

        if (!interactionList.isEmpty()) {
            statsService.addInteractions(interactionList);
            interactionRepository.deleteAll(interactionList);
        }
    }
}
