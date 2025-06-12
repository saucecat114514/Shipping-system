package com.shippingsystem.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统角色实体
 */
@Data
public class SysRole {
    
    /**
     * 角色ID
     */
    private Long id;
    
    /**
     * 角色编码
     */
    private String roleCode;
    
    /**
     * 角色名称
     */
    private String roleName;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
    /**
     * 软删除标记
     */
    private Integer isDeleted;
} 