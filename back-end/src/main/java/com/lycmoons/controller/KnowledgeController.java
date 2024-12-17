package com.lycmoons.controller;
import com.lycmoons.entity.RestBean;
import com.lycmoons.entity.vo.response.KnowledgeVO;
import com.lycmoons.service.KnowledgeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeController {
    @Resource
    KnowledgeService knowledgeService;

    // 知识推送
    @GetMapping("/top-knowledge")
    public RestBean<List<KnowledgeVO>> getTopKnowledge() {
        List<KnowledgeVO> topKnowledge = knowledgeService.getTopKnowledge();

        if (topKnowledge == null) {
            return RestBean.failure(400, "知识推送失败");
        }
        return RestBean.success(topKnowledge);
    }
}
