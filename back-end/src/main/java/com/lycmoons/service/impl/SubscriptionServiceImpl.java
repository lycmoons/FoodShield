package com.lycmoons.service.impl;
import com.lycmoons.entity.dto.Subscription;
import com.lycmoons.mapper.SubscriptionMapper;
import com.lycmoons.service.SubscriptionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Resource
    SubscriptionMapper subscriptionMapper;

    @Override
    public String addSubscription(Subscription subscription) {
        int cnt = subscriptionMapper.addSubscription(subscription);
        if (cnt > 0) {
            return null;
        }
        return "订阅失败";
    }

    @Override
    public String deleteSubscription(Integer userId, Integer foodId) {
        int cnt = subscriptionMapper.deleteSubscription(userId, foodId);
        if (cnt > 0) {
            return null;
        }
        return "取消订阅失败";
    }

    @Override
    public List<Subscription> getSubscriptionByUserId(Integer userId) {
        List<Subscription> subscriptions = subscriptionMapper.getSubscriptionByUserId(userId);
        if (subscriptions.isEmpty()) {
            return null;
        }
        return subscriptions;
    }
}
