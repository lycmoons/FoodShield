package com.lycmoons.controller;

import com.lycmoons.entity.RestBean;
import com.lycmoons.entity.vo.response.NewsVO;
import com.lycmoons.service.NewsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Resource
    NewsService newsService;

    // 新闻推送
    @GetMapping("/top-news")
    public RestBean<List<NewsVO>> getTopNews() {
        List<NewsVO> topNews = newsService.getTopNews();
        if (topNews == null) {
            return RestBean.failure(400, "新闻推送失败");
        }
        return RestBean.success(topNews);
    }
}
