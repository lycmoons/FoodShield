package com.lycmoons.service.impl;

import com.lycmoons.entity.dto.ComplaintDTO;
import com.lycmoons.entity.vo.response.ComplaintVO;
import com.lycmoons.mapper.ComplaintMapper;
import com.lycmoons.service.ComplaintService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Resource
    ComplaintMapper complaintMapper;

    @Override
    public List<ComplaintVO> getComplaintByUserId(Integer userId) {
        List<ComplaintDTO> complaints= complaintMapper.getComplaintByUserId(userId);
        if (complaints==null|| complaints.isEmpty()){
            return null;
        }

        List<ComplaintVO> complaintVOS=new ArrayList<>();
        for (ComplaintDTO complaintDTO:complaints){
            complaintVOS.add(new ComplaintVO(complaintDTO));
        }
        return complaintVOS;
    }

    @Override
    public String addComplaint(ComplaintDTO dto) {
        int cnt = complaintMapper.addComplaint(dto);
        if (cnt>0){
            return null;
        }
        else{
            return "投诉发送失败";
        }
    }
}
