package com.netflix.clon.service;

import com.netflix.clon.model.Subscription;

import java.util.List;

public interface SubscriptionService {

    List<Subscription> getAllSubscriptions();

    Subscription getSubscriptionById(Long id);

    Subscription saveSubscription(Subscription subscription);

    void deleteSubscription(Long id);
}
