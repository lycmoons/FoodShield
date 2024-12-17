package com.lycmoons.mapper;

import com.lycmoons.entity.dto.NewsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NewsMapper {
    // 获取热门新闻的推送
    @Select("select * from News order by id desc limit 3")
    List<NewsDTO> getTopNews();
}
