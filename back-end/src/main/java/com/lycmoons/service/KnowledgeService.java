package com.lycmoons.service;

import com.lycmoons.entity.vo.response.KnowledgeVO;

import java.util.List;

public interface KnowledgeService {
    List<KnowledgeVO> getTopKnowledge();
}
