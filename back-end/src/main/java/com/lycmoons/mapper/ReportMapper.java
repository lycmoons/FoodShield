package com.lycmoons.mapper;

import com.lycmoons.entity.dto.ReportDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReportMapper {
    // 获取食品安全报告的 url
    @Select("select * from Report")
    ReportDTO getReportPhoto();
}
