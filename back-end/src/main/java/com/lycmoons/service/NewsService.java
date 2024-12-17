package com.lycmoons.service;

import com.lycmoons.entity.vo.response.NewsVO;

import java.util.List;

public interface NewsService {
    List<NewsVO> getTopNews();
}
