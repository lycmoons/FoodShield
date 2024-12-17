package com.lycmoons.service.impl;

import com.lycmoons.entity.dto.ReportDTO;
import com.lycmoons.mapper.ReportMapper;
import com.lycmoons.service.ReportService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    @Resource
    ReportMapper reportMapper;

    @Override
    public ReportDTO getReportPhoto() {
        return reportMapper.getReportPhoto();
    }
}
