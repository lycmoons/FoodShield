package com.lycmoons.service.impl;

import com.lycmoons.entity.dto.KnowledgeDTO;
import com.lycmoons.entity.vo.response.KnowledgeVO;
import com.lycmoons.mapper.KnowledgeMapper;
import com.lycmoons.service.KnowledgeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
    @Resource
    KnowledgeMapper knowledgeMapper;

    @Override
    public List<KnowledgeVO> getTopKnowledge() {
        List<KnowledgeDTO> topKnowledge = knowledgeMapper.getTopKnowledge();
        if(topKnowledge == null || topKnowledge.isEmpty()){
            return null;
        }

        List<KnowledgeVO> knowledgeVO = new ArrayList<>();
        for (KnowledgeDTO knowledgeDTO : topKnowledge) {
            knowledgeVO.add(new KnowledgeVO(knowledgeDTO));
        }

        return knowledgeVO;
    }
}
