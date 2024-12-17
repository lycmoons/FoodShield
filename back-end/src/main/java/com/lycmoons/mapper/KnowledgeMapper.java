package com.lycmoons.mapper;

import com.lycmoons.entity.dto.KnowledgeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KnowledgeMapper {
    // 获取热门知识的推送
    @Select("select * from Knowledge order by id desc limit 3")
    List<KnowledgeDTO> getTopKnowledge();
}
