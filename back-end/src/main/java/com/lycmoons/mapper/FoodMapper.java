package com.lycmoons.mapper;
import com.lycmoons.entity.dto.FoodDTO;
import org.apache.ibatis.annotations.*;
import java.util.Date;
import java.util.List;

@Mapper
public interface FoodMapper {
    // 获取整个 Food 表的内容
    @Select("select * from Food")
    List<FoodDTO> getAllFood();

    // 删除一条食品数据
    @Delete("delete from Food where id = #{id}")
    int deleteFoodById(@Param("id") Integer id);

    // 添加一条食品数据
    @Insert("insert into Food values (#{id},#{name},#{date},#{manufacturer},#{batch_num},#{photo_url},#{materialSafety},#{productionSafety},#{processingSafety},#{transportationSafety})")
    int addFood(FoodDTO food);

    // 修改食品信息
    @Update("update Food set name = #{name}, date = #{date}, manufacturer = #{manufacturer}, batch_num = #{batch_num}, photo_url = #{photo_url}, materialSafety = #{materialSafety}, productionSafety = #{productionSafety}, processingSafety = #{processingSafety}, transportationSafety = #{transportationSafety} where id = #{id}")
    int updateFood(@Param("id") Integer id,
                   @Param("name") String name,
                   @Param("date") Date date,
                   @Param("manufacturer") String manufacturer,
                   @Param("batch_num") Integer batch_num,
                   @Param("photo_url") String photo_url,
                   @Param("materialSafety") String materialSafety,
                   @Param("productionSafety") String productionSafety,
                   @Param("processingSafety") String processingSafety,
                   @Param("transportationSafety") String transportationSafety
    );
}
