package com.lycmoons.mapper;

import com.lycmoons.entity.dto.MessageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {
    // 查找指定用户的消息
    @Select("select * from Message where user_id = #{id} order by id desc")
    List<MessageDTO> findMessageByUserId(@Param("id") Integer id);
}
