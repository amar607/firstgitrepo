package com.amar.blog.service;

import com.amar.blog.dto.SubscriptionDTO;
import com.amar.blog.entity.Subscription;
import com.amar.blog.repository.SubscriptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{

    private SubscriptionRepository subscriptionRepository;

    private ModelMapper mapper;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, ModelMapper mapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.mapper = mapper;
    }
    @Override
    public String subscribe(SubscriptionDTO subscriptionDTO) {
        if (subscriptionDTO == null || subscriptionDTO.getEmail().isBlank()) {
            return "Invalid subscription data.";
        }

        Subscription subscription = subscriptionRepository.findByEmail(subscriptionDTO.getEmail());

        if (subscription == null) {
            Subscription subscriptionEntity = mapper.map(subscriptionDTO, Subscription.class);
            subscriptionRepository.save(subscriptionEntity);
            return "Thank you for subscribing.";
        } else {
            return "You are already Subscribed.";
        }
    }
}
