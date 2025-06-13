package com.shippingsystem.service;

import com.github.pagehelper.PageInfo;
import com.shippingsystem.dto.PortDTO;
import com.shippingsystem.dto.PortQueryDTO;
import com.shippingsystem.entity.Port;

import java.util.List;

/**
 * 港口服务接口
 */
public interface PortService {
    
    /**
     * 根据ID查询港口
     * @param id 港口ID
     * @return 港口信息
     */
    Port getById(Long id);
    
    /**
     * 根据港口代码查询港口
     * @param portCode 港口代码
     * @return 港口信息
     */
    Port getByPortCode(String portCode);
    
    /**
     * 分页查询港口列表
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    PageInfo<Port> getByPage(PortQueryDTO queryDTO);
    
    /**
     * 获取所有港口（用于下拉选择）
     * @return 港口列表
     */
    List<Port> getAllPorts();
    
    /**
     * 创建港口
     * @param portDTO 港口DTO
     * @return 创建的港口
     */
    Port create(PortDTO portDTO);
    
    /**
     * 更新港口
     * @param id 港口ID
     * @param portDTO 港口DTO
     * @return 更新的港口
     */
    Port update(Long id, PortDTO portDTO);
    
    /**
     * 删除港口
     * @param id 港口ID
     */
    void delete(Long id);
    
    /**
     * 批量删除港口
     * @param ids 港口ID列表
     */
    void deleteBatch(List<Long> ids);
    
    /**
     * 检查港口代码是否存在
     * @param portCode 港口代码
     * @param excludeId 排除的ID（用于更新时检查）
     * @return 是否存在
     */
    boolean existsByPortCode(String portCode, Long excludeId);
} 