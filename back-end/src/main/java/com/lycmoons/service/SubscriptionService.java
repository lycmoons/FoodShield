package com.lycmoons.service;
import com.lycmoons.entity.dto.Subscription;
import java.util.List;

public interface SubscriptionService {
    String addSubscription(Subscription subscription);
    String deleteSubscription(Integer userId, Integer foodId);
    List<Subscription> getSubscriptionByUserId(Integer userId);
}
