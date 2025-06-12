package com.shippingsystem.mapper;

import com.shippingsystem.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户Mapper接口
 */
@Mapper
public interface SysUserMapper {
    
    /**
     * 根据ID查询用户
     */
    SysUser selectById(@Param("id") Long id);
    
    /**
     * 根据用户名查询用户
     */
    SysUser selectByUsername(@Param("username") String username);
    
    /**
     * 根据邮箱查询用户
     */
    SysUser selectByEmail(@Param("email") String email);
    
    /**
     * 插入用户
     */
    int insert(SysUser user);
    
    /**
     * 更新用户
     */
    int updateById(SysUser user);
    
    /**
     * 根据ID删除用户（物理删除）
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据ID软删除用户
     */
    int softDeleteById(@Param("id") Long id);
    
    /**
     * 查询用户列表
     */
    List<SysUser> selectList(@Param("username") String username, 
                            @Param("email") String email, 
                            @Param("status") Integer status);
    
    /**
     * 统计用户数量
     */
    long countUsers(@Param("username") String username, 
                   @Param("email") String email, 
                   @Param("status") Integer status);
    
    /**
     * 批量更新用户状态
     */
    int batchUpdateStatus(@Param("userIds") List<Long> userIds, @Param("status") Integer status);
} 