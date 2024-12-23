package com.lycmoons.service;
import com.lycmoons.entity.dto.Subscription;
import com.lycmoons.entity.vo.request.DeleteFoodVO;
import com.lycmoons.entity.vo.response.FoodVO;
import java.util.List;

public interface FoodService {
    List<FoodVO> getFoodList(List<Subscription> subscriptions);
    String deleteFood(DeleteFoodVO vo, Integer adminId);
    String addFood(String name,
                   String date,
                   String manufacturer,
                   Integer batchNum,
                   String photo_url,
                   String materialSafety,
                   String productionSafety,
                   String processingSafety,
                   String transportationSafety,
                   Integer adminId
    );

    String updateFood(Integer id,
                   String name,
                   String date,
                   String manufacturer,
                   Integer batchNum,
                   String photo_url,
                   String materialSafety,
                   String productionSafety,
                   String processingSafety,
                   String transportationSafety,
                   Integer adminId
    );
}
