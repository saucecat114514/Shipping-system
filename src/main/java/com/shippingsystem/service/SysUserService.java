package com.shippingsystem.service;

import com.shippingsystem.entity.SysUser;
import com.shippingsystem.utils.PageResult;

import java.util.List;

/**
 * 系统用户服务接口
 */
public interface SysUserService {
    
    /**
     * 根据ID查询用户
     */
    SysUser getUserById(Long id);
    
    /**
     * 根据用户名查询用户
     */
    SysUser getUserByUsername(String username);
    
    /**
     * 根据用户名查询用户（包含角色信息）
     */
    SysUser getUserWithRolesByUsername(String username);
    
    /**
     * 创建用户
     */
    Long createUser(SysUser user, List<Long> roleIds);
    
    /**
     * 更新用户信息
     */
    boolean updateUser(SysUser user);
    
    /**
     * 更新用户角色
     */
    boolean updateUserRoles(Long userId, List<Long> roleIds);
    
    /**
     * 删除用户
     */
    boolean deleteUser(Long id);
    
    /**
     * 启用/禁用用户
     */
    boolean updateUserStatus(Long id, Integer status);
    
    /**
     * 重置用户密码
     */
    boolean resetPassword(Long id, String newPassword);
    
    /**
     * 修改密码
     */
    boolean changePassword(Long id, String oldPassword, String newPassword);
    
    /**
     * 分页查询用户列表
     */
    PageResult<SysUser> getUserList(int pageNum, int pageSize, String username, String email, Integer status);
    
    /**
     * 验证用户名是否唯一
     */
    boolean isUsernameUnique(String username, Long excludeId);
    
    /**
     * 验证邮箱是否唯一
     */
    boolean isEmailUnique(String email, Long excludeId);
    
    /**
     * 批量更新用户状态
     */
    boolean batchUpdateStatus(List<Long> userIds, Integer status);
} 