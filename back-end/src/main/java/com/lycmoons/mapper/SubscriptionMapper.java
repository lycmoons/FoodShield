package com.lycmoons.mapper;
import com.lycmoons.entity.dto.Subscription;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface SubscriptionMapper {
    // 插入订阅信息
    @Insert("insert into Subscription values (#{user_id}, #{food_id})")
    int addSubscription(Subscription subscription);

    // 获取用户的所有订阅信息
    @Select("select * from Subscription where user_id = #{user_id}")
    List<Subscription> getSubscriptionByUserId(@Param("user_id") Integer userId);

    // 获取某个食品的所有订阅信息
    @Select("select * from Subscription where food_id = #{food_id}")
    List<Subscription> getSubscriptionByFoodId(@Param("food_id") Integer foodId);

    // 删除用户的订阅信息
    @Delete("delete from Subscription where user_id = #{user_id} and food_id = #{food_id}")
    int deleteSubscription(@Param("user_id") Integer userId, @Param("food_id") Integer foodId);
}
