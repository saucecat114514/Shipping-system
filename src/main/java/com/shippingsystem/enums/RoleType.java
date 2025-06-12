package com.shippingsystem.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色类型枚举
 */
@Getter
@AllArgsConstructor
public enum RoleType {
    
    /**
     * 系统管理员
     */
    ADMIN("ADMIN", "系统管理员"),
    
    /**
     * 航运调度员
     */
    DISPATCHER("DISPATCHER", "航运调度员"),
    
    /**
     * 客户
     */
    CUSTOMER("CUSTOMER", "客户");

    private final String code;
    private final String description;

    /**
     * 根据角色编码获取枚举
     */
    public static RoleType getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (RoleType role : values()) {
            if (role.code.equals(code)) {
                return role;
            }
        }
        return null;
    }
}