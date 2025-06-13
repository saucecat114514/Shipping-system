package com.shippingsystem.service;

import com.shippingsystem.dto.VesselTypeDictDTO;
import com.shippingsystem.entity.VesselTypeDict;

import java.util.List;

/**
 * 船舶类型字典服务接口
 */
public interface VesselTypeDictService {
    
    /**
     * 根据ID查询船舶类型
     * @param id 类型ID
     * @return 船舶类型信息
     */
    VesselTypeDict getById(Long id);
    
    /**
     * 根据类型代码查询船舶类型
     * @param typeCode 类型代码
     * @return 船舶类型信息
     */
    VesselTypeDict getByTypeCode(String typeCode);
    
    /**
     * 获取所有船舶类型
     * @return 船舶类型列表
     */
    List<VesselTypeDict> getAllTypes();
    
    /**
     * 创建船舶类型
     * @param vesselTypeDictDTO 船舶类型DTO
     * @return 创建的船舶类型
     */
    VesselTypeDict create(VesselTypeDictDTO vesselTypeDictDTO);
    
    /**
     * 更新船舶类型
     * @param id 类型ID
     * @param vesselTypeDictDTO 船舶类型DTO
     * @return 更新的船舶类型
     */
    VesselTypeDict update(Long id, VesselTypeDictDTO vesselTypeDictDTO);
    
    /**
     * 删除船舶类型
     * @param id 类型ID
     */
    void delete(Long id);
    
    /**
     * 检查类型代码是否存在
     * @param typeCode 类型代码
     * @param excludeId 排除的ID（用于更新时检查）
     * @return 是否存在
     */
    boolean existsByTypeCode(String typeCode, Long excludeId);
} 