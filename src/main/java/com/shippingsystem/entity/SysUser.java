package com.shippingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户实体
 */
@Data
public class SysUser {
    
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码哈希值
     */
    @JsonIgnore
    private String passwordHash;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 状态：0=禁用 1=启用
     */
    private Integer status;
    
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
    @JsonIgnore
    private Integer isDeleted;
    
    /**
     * 用户角色列表（非数据库字段）
     */
    private List<SysRole> roles;
    
    /**
     * 获取第一个角色的编码（用于权限判断）
     */
    public String getRoleCode() {
        if (roles != null && !roles.isEmpty()) {
            return roles.get(0).getRoleCode();
        }
        return null;
    }
    
    /**
     * 判断用户是否启用
     */
    public boolean isEnabled() {
        return Integer.valueOf(1).equals(status);
    }
    
    /**
     * 判断用户是否为管理员
     */
    public boolean isAdmin() {
        return "ADMIN".equals(getRoleCode());
    }
    
    /**
     * 判断用户是否为调度员
     */
    public boolean isDispatcher() {
        return "DISPATCHER".equals(getRoleCode());
    }
    
    /**
     * 判断用户是否为客户
     */
    public boolean isCustomer() {
        return "CUSTOMER".equals(getRoleCode());
    }
} 