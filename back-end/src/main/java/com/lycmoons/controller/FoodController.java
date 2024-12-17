package com.lycmoons.controller;

import com.lycmoons.entity.RestBean;
import com.lycmoons.entity.vo.response.FoodVO;
import com.lycmoons.service.FoodService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Resource
    FoodService foodService;

    // 获取所有食物信息
    @GetMapping("/all-food")
    public RestBean<List<FoodVO>> getAllFood() {
        List<FoodVO> foodList = foodService.getFoodList();
        if (foodList == null){
            return RestBean.failure(400, "未找到任何食品信息");
        }
        return RestBean.success(foodList);
    }
}
