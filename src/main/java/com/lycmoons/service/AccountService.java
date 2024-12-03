package com.lycmoons.service;

import com.lycmoons.entity.vo.request.RegisterVO;
import com.lycmoons.entity.vo.request.ResetPasswordVO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {
    String register(RegisterVO vo);
    String resetPassword(ResetPasswordVO vo);
}
