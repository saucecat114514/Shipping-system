package com.shippingsystem.service;

import com.github.pagehelper.PageInfo;
import com.shippingsystem.dto.VesselDTO;
import com.shippingsystem.dto.VesselQueryDTO;
import com.shippingsystem.entity.Vessel;

import java.util.List;

/**
 * 船舶服务接口
 */
public interface VesselService {
    
    /**
     * 根据ID查询船舶
     * @param id 船舶ID
     * @return 船舶信息
     */
    Vessel getById(Long id);
    
    /**
     * 根据MMSI查询船舶
     * @param mmsi MMSI号码
     * @return 船舶信息
     */
    Vessel getByMmsi(String mmsi);
    
    /**
     * 根据IMO查询船舶
     * @param imo IMO号码
     * @return 船舶信息
     */
    Vessel getByImo(String imo);
    
    /**
     * 分页查询船舶列表
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    PageInfo<Vessel> getByPage(VesselQueryDTO queryDTO);
    
    /**
     * 获取所有可用船舶（用于下拉选择）
     * @return 船舶列表
     */
    List<Vessel> getAllVessels();
    
    /**
     * 创建船舶
     * @param vesselDTO 船舶DTO
     * @return 创建的船舶
     */
    Vessel create(VesselDTO vesselDTO);
    
    /**
     * 更新船舶
     * @param id 船舶ID
     * @param vesselDTO 船舶DTO
     * @return 更新的船舶
     */
    Vessel update(Long id, VesselDTO vesselDTO);
    
    /**
     * 删除船舶
     * @param id 船舶ID
     */
    void delete(Long id);
    
    /**
     * 批量删除船舶
     * @param ids 船舶ID列表
     */
    void deleteBatch(List<Long> ids);
    
    /**
     * 检查MMSI是否存在
     * @param mmsi MMSI号码
     * @param excludeId 排除的ID（用于更新时检查）
     * @return 是否存在
     */
    boolean existsByMmsi(String mmsi, Long excludeId);
    
    /**
     * 检查IMO是否存在
     * @param imo IMO号码
     * @param excludeId 排除的ID（用于更新时检查）
     * @return 是否存在
     */
    boolean existsByImo(String imo, Long excludeId);
} 