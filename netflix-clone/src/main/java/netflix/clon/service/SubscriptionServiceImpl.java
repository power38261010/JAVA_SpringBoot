package com.netflix.clon.service;

import com.netflix.clon.model.Subscription;
import com.netflix.clon.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription getSubscriptionById(Long id) {
        Optional<Subscription> subscriptionOptional = subscriptionRepository.findById(id);
        return subscriptionOptional.orElse(null);
    }

    @Override
    public Subscription saveSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription updateSubscription(Long id, Subscription subscription) {
        Subscription existingSubscription = getSubscriptionById(id);
        if (existingSubscription != null) {
            existingSubscription.setType(subscription.getType());
            existingSubscription.setPrice(subscription.getPrice());
            return subscriptionRepository.save(existingSubscription);
        }
        return null; // Subscription not found
    }

    @Override
    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }
}
