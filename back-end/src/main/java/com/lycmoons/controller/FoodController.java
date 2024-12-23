package com.lycmoons.controller;
import com.lycmoons.entity.RestBean;
import com.lycmoons.entity.dto.Subscription;
import com.lycmoons.entity.vo.request.DeleteFoodVO;
import com.lycmoons.entity.vo.response.FoodVO;
import com.lycmoons.service.FoodService;
import com.lycmoons.service.PhotoService;
import com.lycmoons.service.SubscriptionService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Resource
    FoodService foodService;

    @Resource
    SubscriptionService subscriptionService;

    @Resource
    PhotoService photoService;

    // 获取所有食物信息
    @GetMapping("/all-food")
    public RestBean<List<FoodVO>> getAllFood(HttpServletRequest request) {
        // 先获取用户的所有订阅信息
        Integer userId = (Integer) request.getAttribute("id");
        List<Subscription> subscriptions = subscriptionService.getSubscriptionByUserId(userId);

        // 根据订阅信息来附加每条食品是否已经被这个用户订阅的属性信息
        List<FoodVO> foodList = foodService.getFoodList(subscriptions);
        if (foodList == null){
            return RestBean.failure(400, "未找到任何食品信息");
        }
        return RestBean.success(foodList);
    }

    // 删除一系列食品信息
    @PostMapping("/delete-food")
    public RestBean<Void> deleteFood(HttpServletRequest request,
                                     @RequestBody DeleteFoodVO vo) {
        Integer adminId = (Integer) request.getAttribute("id");
        if (adminId == null){
            return RestBean.failure(400, "身份认证失败");
        }
        String msg = foodService.deleteFood(vo, adminId);
        if (msg == null){
            return RestBean.success(null);
        }
        return RestBean.failure(400, msg);
    }

    // 食品信息的录入
    @PostMapping("/add-food")
    public RestBean<Void> addFood(HttpServletRequest request,
                                  @RequestParam("name") String name,
                                  @RequestParam("date") String date,
                                  @RequestParam("manufacturer") String manufacturer,
                                  @RequestParam("batch_num") Integer batchNum,
                                  @RequestParam("image") MultipartFile[] image,
                                  @RequestParam("materialSafety") String materialSafety,
                                  @RequestParam("productionSafety") String productionSafety,
                                  @RequestParam("processingSafety") String processingSafety,
                                  @RequestParam("transportationSafety") String transportationSafety
    ){
        Integer adminId = (Integer) request.getAttribute("id");
        if (adminId == null){
            return RestBean.failure(400, "身份认证失败");
        }
        try {
            String photo_url = photoService.storePhoto(image);
            String msg = foodService.addFood(name,date,manufacturer,batchNum,photo_url,materialSafety,productionSafety,processingSafety,transportationSafety,adminId);
            if (msg == null){
                return RestBean.success(null);
            }
            return RestBean.failure(400, msg);
        } catch (IOException e) {
            return RestBean.failure(400, e.getMessage());
        }
    }


    // 食品信息的修改
    @PostMapping("/update-food")
    public RestBean<Void> updateFood(HttpServletRequest request,
                                  @RequestParam("id") Integer id,
                                  @RequestParam("name") String name,
                                  @RequestParam("date") String date,
                                  @RequestParam("manufacturer") String manufacturer,
                                  @RequestParam("batch_num") Integer batchNum,
                                  @RequestParam("image") MultipartFile[] image,
                                  @RequestParam("materialSafety") String materialSafety,
                                  @RequestParam("productionSafety") String productionSafety,
                                  @RequestParam("processingSafety") String processingSafety,
                                  @RequestParam("transportationSafety") String transportationSafety
    ){
        Integer adminId = (Integer) request.getAttribute("id");
        if (adminId == null){
            return RestBean.failure(400, "身份认证失败");
        }

        try {
            String photo_url = photoService.storePhoto(image);

            // 食品信息修改业务
            String msg = foodService.updateFood(id,name,date,manufacturer,batchNum,photo_url,materialSafety,productionSafety,processingSafety,transportationSafety,adminId);
            if (msg == null){
                return RestBean.success(null);
            }
            return RestBean.failure(400, msg);
        } catch (IOException e) {
            return RestBean.failure(400, e.getMessage());
        }
    }
}