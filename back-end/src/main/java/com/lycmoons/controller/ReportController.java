package com.lycmoons.controller;

import com.lycmoons.entity.RestBean;
import com.lycmoons.entity.dto.ReportDTO;
import com.lycmoons.service.ReportService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Resource
    ReportService reportService;

    // 获取食品安全报告
    @GetMapping("/get-report")
    public RestBean<ReportDTO> getReport() {
        ReportDTO photo = reportService.getReportPhoto();

        if (photo == null){
            return RestBean.failure(400, "食品安全报告获取失败");
        }
        return RestBean.success(photo);
    }
}
