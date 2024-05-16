package com.netflix.clon.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netflix.clon.model.Subscription;
import com.netflix.clon.repository.SubscriptionRepository;

/**
 *
 * @author alejandro
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

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

    @Override
    @Transactional
    public Subscription findOrCreateSubscription(String type, double price) {
        try {
            Subscription existingSubscription = subscriptionRepository.findByType(type);

            if (existingSubscription != null) {
                return existingSubscription;
            } else {
                Subscription newSubscription = new Subscription(type, price);
                subscriptionRepository.save(newSubscription);
                return newSubscription;
            }
        } catch (Exception e) {
            logger.error("Error al buscar o crear sub: " + e.getMessage(), e);
            throw new RuntimeException("Error al buscar o crear sub", e);
        }
    }
}
