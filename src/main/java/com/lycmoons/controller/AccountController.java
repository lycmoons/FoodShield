package com.lycmoons.controller;

import com.lycmoons.entity.RestBean;
import com.lycmoons.entity.vo.request.RegisterVO;
import com.lycmoons.entity.vo.request.ResetPasswordVO;
import com.lycmoons.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Resource
    AccountService accountService;

    @PostMapping("/register")
    public RestBean<Void> register(@RequestBody RegisterVO vo){
        String msg = accountService.register(vo);

        if(msg == null) return RestBean.success(null);
        else return RestBean.failure(400, msg);
    }

    @PostMapping("/reset-password")
    public RestBean<Void> resetPassword(@RequestBody ResetPasswordVO vo){
        String msg = accountService.resetPassword(vo);

        if(msg == null) return RestBean.success(null);
        else return RestBean.failure(400, msg);
    }
}
