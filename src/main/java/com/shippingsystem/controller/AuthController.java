package com.shippingsystem.controller;

import com.shippingsystem.dto.LoginRequest;
import com.shippingsystem.dto.LoginResponse;
import com.shippingsystem.service.AuthService;
import com.shippingsystem.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    
    private final AuthService authService;
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("用户登录: {}", loginRequest.getUsername());
        LoginResponse loginResponse = authService.login(loginRequest);
        return ApiResponse.success("登录成功", loginResponse);
    }
    
    /**
     * 刷新令牌
     */
    @PostMapping("/refresh")
    public ApiResponse<LoginResponse> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        log.info("刷新令牌");
        LoginResponse loginResponse = authService.refreshToken(refreshToken);
        return ApiResponse.success("令牌刷新成功", loginResponse);
    }
    
    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestHeader("Authorization") String authorization) {
        log.info("用户登出");
        String token = authorization.replace("Bearer ", "");
        authService.logout(token);
        return ApiResponse.success("登出成功");
    }
    
    /**
     * 获取当前用户信息
     */
    @GetMapping("/me")
    public ApiResponse<LoginResponse.UserInfo> getCurrentUser() {
        Long userId = authService.getCurrentUserId();
        String username = authService.getCurrentUsername();
        String roleCode = authService.getCurrentUserRole();
        
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo();
        userInfo.setId(userId);
        userInfo.setUsername(username);
        userInfo.setRoleCode(roleCode);
        
        return ApiResponse.success("获取用户信息成功", userInfo);
    }
} 