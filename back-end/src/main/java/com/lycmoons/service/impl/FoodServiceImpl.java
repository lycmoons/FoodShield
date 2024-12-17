package com.lycmoons.service.impl;

import com.lycmoons.entity.dto.FoodDTO;
import com.lycmoons.entity.vo.response.FoodVO;
import com.lycmoons.mapper.FoodMapper;
import com.lycmoons.service.FoodService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {
    @Resource
    FoodMapper foodMapper;

    @Override
    public List<FoodVO> getFoodList() {
        List<FoodDTO> allFood = foodMapper.getAllFood();
        if (allFood == null || allFood.isEmpty()) {
            return null;
        }

        List<FoodVO> foodVOList = new ArrayList<>();
        for (FoodDTO foodDTO : allFood) {
            foodVOList.add(new FoodVO(foodDTO));
        }
        return foodVOList;
    }
}
