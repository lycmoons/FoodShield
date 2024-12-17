package com.lycmoons.mapper;

import com.lycmoons.entity.dto.ComplaintDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ComplaintMapper {
    // 查找指定用户的投诉信息
    @Select("select * from Complaint where user_id = #{id} order by id desc")
    List<ComplaintDTO> getComplaintByUserId(@Param("id") Integer id);

    // 插入一条投诉信息
    @Insert("insert into Complaint values (#{id}, #{user_id}, #{content}, #{photo_url}, #{date})")
    int addComplaint(ComplaintDTO dto);
}
