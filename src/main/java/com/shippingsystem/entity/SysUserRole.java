package com.shippingsystem.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户角色关联实体
 */
@Data
public class SysUserRole {
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 角色ID
     */
    private Long roleId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
} 