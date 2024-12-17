package com.lycmoons.mapper;

import com.lycmoons.entity.dto.FoodDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface FoodMapper {
    // 获取整个 Food 表的内容
    @Select("select * from Food")
    List<FoodDTO> getAllFood();
}
