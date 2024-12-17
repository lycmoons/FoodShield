package com.lycmoons.service.impl;

import com.lycmoons.entity.dto.NewsDTO;
import com.lycmoons.entity.vo.response.NewsVO;
import com.lycmoons.mapper.NewsMapper;
import com.lycmoons.service.NewsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Resource
    NewsMapper newsMapper;

    @Override
    public List<NewsVO> getTopNews() {
        List<NewsDTO> topNews = newsMapper.getTopNews();
        if (topNews == null || topNews.isEmpty()) {
            return null;
        }

        List<NewsVO> topNewsVO = new ArrayList<>();
        for (NewsDTO newsDTO : topNews) {
            topNewsVO.add(new NewsVO(newsDTO));
        }

        return topNewsVO;
    }
}
