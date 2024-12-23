package com.lycmoons.controller;
import com.lycmoons.entity.RestBean;
import com.lycmoons.entity.dto.Subscription;
import com.lycmoons.entity.vo.request.SubscriptVO;
import com.lycmoons.service.SubscriptionService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
    @Resource
    SubscriptionService subscriptionService;

    // 订阅
    @PostMapping("/subscript")
    public RestBean<Void> subscribe(HttpServletRequest request,
                                    @RequestBody SubscriptVO vo) {
        Integer userId = (Integer) request.getAttribute("id");
        String msg = subscriptionService.addSubscription(new Subscription(userId, vo.getFood_id()));
        if (msg == null){
            return RestBean.success(null);
        }
        return RestBean.failure(400, msg);
    }

    // 取消订阅
    @PostMapping("undo-subscript")
    public RestBean<Void> undoSubscript(HttpServletRequest request,
                                        @RequestBody SubscriptVO vo) {
        Integer userId = (Integer) request.getAttribute("id");
        String msg = subscriptionService.deleteSubscription(userId, vo.getFood_id());
        if (msg == null){
            return RestBean.success(null);
        }
        return RestBean.failure(400, msg);
    }
}
