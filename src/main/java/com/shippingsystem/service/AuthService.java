package com.shippingsystem.service;

import com.shippingsystem.dto.LoginRequest;
import com.shippingsystem.dto.LoginResponse;

/**
 * 认证服务接口
 */
public interface AuthService {
    
    /**
     * 用户登录
     */
    LoginResponse login(LoginRequest loginRequest);
    
    /**
     * 刷新令牌
     */
    LoginResponse refreshToken(String refreshToken);
    
    /**
     * 用户登出
     */
    boolean logout(String token);
    
    /**
     * 验证令牌
     */
    boolean validateToken(String token);
    
    /**
     * 获取当前登录用户ID
     */
    Long getCurrentUserId();
    
    /**
     * 获取当前登录用户名
     */
    String getCurrentUsername();
    
    /**
     * 获取当前用户角色
     */
    String getCurrentUserRole();
} 