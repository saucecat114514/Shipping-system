package com.shippingsystem.mapper;

import com.shippingsystem.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统角色Mapper接口
 */
@Mapper
public interface SysRoleMapper {
    
    /**
     * 根据ID查询角色
     */
    SysRole selectById(@Param("id") Long id);
    
    /**
     * 根据角色编码查询角色
     */
    SysRole selectByCode(@Param("roleCode") String roleCode);
    
    /**
     * 根据用户ID查询角色列表
     */
    List<SysRole> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 查询所有角色
     */
    List<SysRole> selectAll();
    
    /**
     * 插入角色
     */
    int insert(SysRole role);
    
    /**
     * 更新角色
     */
    int updateById(SysRole role);
    
    /**
     * 根据ID删除角色
     */
    int deleteById(@Param("id") Long id);
} 