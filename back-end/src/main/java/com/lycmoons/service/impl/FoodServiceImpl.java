package com.lycmoons.service.impl;
import com.lycmoons.entity.dto.FoodDTO;
import com.lycmoons.entity.dto.LogDTO;
import com.lycmoons.entity.dto.MessageDTO;
import com.lycmoons.entity.dto.Subscription;
import com.lycmoons.entity.vo.request.DeleteFoodVO;
import com.lycmoons.entity.vo.response.FoodVO;
import com.lycmoons.mapper.FoodMapper;
import com.lycmoons.mapper.LogMapper;
import com.lycmoons.mapper.MessageMapper;
import com.lycmoons.mapper.SubscriptionMapper;
import com.lycmoons.service.FoodService;
import com.lycmoons.util.Convertor;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {
    @Resource
    FoodMapper foodMapper;

    @Resource
    SubscriptionMapper subscriptionMapper;

    @Resource
    MessageMapper messageMapper;

    @Resource
    LogMapper logMapper;


    @Override
    public List<FoodVO> getFoodList(List<Subscription> subscriptions) {
        List<FoodDTO> allFood = foodMapper.getAllFood();
        if (allFood == null || allFood.isEmpty()) {
            return null;
        }

        List<FoodVO> foodVOList = new ArrayList<>();

        // 没有任何订阅信息
        if (subscriptions == null || subscriptions.isEmpty()) {
            for (FoodDTO foodDTO : allFood) {
                foodVOList.add(new FoodVO(foodDTO, 0));
            }
            return foodVOList;
        }

        // 存储所有被订阅的食品的 id，方便后续查找
        HashSet<Integer> subscribedIds = new HashSet<>();
        for (Subscription subscription : subscriptions) {
            subscribedIds.add(subscription.getFood_id());
        }

        for (FoodDTO foodDTO : allFood) {
            // 这个食品被订阅了
            if (subscribedIds.contains(foodDTO.getId())){
                foodVOList.add(new FoodVO(foodDTO, 1));
            }

            // 这个食品没被订阅
            else {
                foodVOList.add(new FoodVO(foodDTO, 0));
            }
        }
        return foodVOList;
    }

    @Override
    public String deleteFood(DeleteFoodVO vo, Integer adminId) {
        // 删除数据
        int cnt = 0;
        for (Integer foodId : vo.getFoodIds()) {
            cnt += foodMapper.deleteFoodById(foodId);
        }
        if (cnt > 0) {
            // 删除成功后进行管理员日志记录
            logMapper.addLog(new LogDTO(null, adminId, "Food", "删除食品信息", new Date()));

            // 通知订阅了这些食品的用户
            HashSet<Integer> userIds = new HashSet<>();
            for (Integer foodId : vo.getFoodIds()) {
                List<Subscription> subscriptions = subscriptionMapper.getSubscriptionByFoodId(foodId);
                if (subscriptions == null || subscriptions.isEmpty()) {
                    continue;
                }
                for (Subscription subscription : subscriptions) {
                    userIds.add(subscription.getUser_id());
                }
            }
            for (Integer userId : userIds) {
                messageMapper.addMessage(new MessageDTO(null, userId, "系统通知：您的订阅食品信息被删除了", new Date()));
            }

            return null;
        }
        else {  //删除失败
            return "食品删除失败";
        }
    }

    @Override
    public String addFood(String name, String date, String manufacturer, Integer batchNum, String photo_url, String materialSafety, String productionSafety, String processingSafety, String transportationSafety, Integer adminId) {
        // 插入数据
        try {
            Date d = Convertor.convertDateString(date);
            int cnt = foodMapper.addFood(new FoodDTO(null, name, d, manufacturer, batchNum, photo_url, materialSafety, productionSafety, processingSafety, transportationSafety));
            if (cnt <= 0) {
                return "食品信息录入失败";
            }
        } catch (ParseException e) {
            return e.getMessage();
        }

        // 管理员日志
        logMapper.addLog(new LogDTO(null, adminId, "Food", "食品信息录入", new Date()));
        return null;
    }

    @Override
    public String updateFood(Integer id, String name, String date, String manufacturer, Integer batchNum, String photo_url, String materialSafety, String productionSafety, String processingSafety, String transportationSafety, Integer adminId) {
        // 修改食品信息
        try {
            Date d = Convertor.convertDateString(date);
            int cnt = foodMapper.updateFood(id,name,d,manufacturer,batchNum,photo_url,materialSafety,productionSafety,processingSafety,transportationSafety);
            if (cnt > 0){
                // 通知订阅该食品的用户
                List<Subscription> subscriptions = subscriptionMapper.getSubscriptionByFoodId(id);
                if (subscriptions != null && !subscriptions.isEmpty()) {
                    for (Subscription subscription : subscriptions) {
                        messageMapper.addMessage(new MessageDTO(null, subscription.getUser_id(),"系统通知：您订阅的食品信息有更新",new Date()));
                    }
                }

                // 管理员日志
                logMapper.addLog(new LogDTO(null, adminId, "Food", "食品信息的更新", new Date()));
                return null;
            }
            return "食品信息修改失败";
        } catch (ParseException e) {
            return e.getMessage();
        }
    }
}
