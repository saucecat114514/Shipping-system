package com.shippingsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shippingsystem.dto.VesselDTO;
import com.shippingsystem.dto.VesselQueryDTO;
import com.shippingsystem.entity.Vessel;
import com.shippingsystem.entity.VesselTypeDict;
import com.shippingsystem.exception.BusinessException;
import com.shippingsystem.mapper.VesselMapper;
import com.shippingsystem.service.VesselService;
import com.shippingsystem.service.VesselTypeDictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 船舶服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VesselServiceImpl implements VesselService {
    
    private final VesselMapper vesselMapper;
    private final VesselTypeDictService vesselTypeDictService;
    
    @Override
    public Vessel getById(Long id) {
        if (id == null) {
            throw new BusinessException("船舶ID不能为空");
        }
        Vessel vessel = vesselMapper.selectById(id);
        if (vessel == null) {
            throw new BusinessException("船舶不存在");
        }
        return vessel;
    }
    
    @Override
    public Vessel getByMmsi(String mmsi) {
        if (mmsi == null || mmsi.trim().isEmpty()) {
            throw new BusinessException("MMSI号码不能为空");
        }
        return vesselMapper.selectByMmsi(mmsi);
    }
    
    @Override
    public Vessel getByImo(String imo) {
        if (imo == null || imo.trim().isEmpty()) {
            throw new BusinessException("IMO号码不能为空");
        }
        return vesselMapper.selectByImo(imo);
    }
    
    @Override
    public PageInfo<Vessel> getByPage(VesselQueryDTO queryDTO) {
        // 设置默认分页参数
        if (queryDTO.getPageNum() == null || queryDTO.getPageNum() <= 0) {
            queryDTO.setPageNum(1);
        }
        if (queryDTO.getPageSize() == null || queryDTO.getPageSize() <= 0) {
            queryDTO.setPageSize(10);
        }
        
        // 开始分页
        PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
        List<Vessel> vessels = vesselMapper.selectByPage(queryDTO);
        return new PageInfo<>(vessels);
    }
    
    @Override
    public List<Vessel> getAllVessels() {
        return vesselMapper.selectAll();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Vessel create(VesselDTO vesselDTO) {
        // 验证船舶类型是否存在
        VesselTypeDict vesselType = vesselTypeDictService.getById(vesselDTO.getTypeId());
        
        // 验证MMSI是否已存在
        if (vesselMapper.countByMmsi(vesselDTO.getMmsi(), null) > 0) {
            throw new BusinessException("MMSI号码已存在: " + vesselDTO.getMmsi());
        }
        
        // 验证IMO是否已存在（如果提供）
        if (vesselDTO.getImo() != null && !vesselDTO.getImo().trim().isEmpty()) {
            if (vesselMapper.countByImo(vesselDTO.getImo(), null) > 0) {
                throw new BusinessException("IMO号码已存在: " + vesselDTO.getImo());
            }
        }
        
        // 创建船舶实体
        Vessel vessel = new Vessel();
        BeanUtils.copyProperties(vesselDTO, vessel);
        
        // 插入数据库
        int result = vesselMapper.insert(vessel);
        if (result != 1) {
            throw new BusinessException("船舶创建失败");
        }
        
        log.info("船舶创建成功: {}", vessel);
        return getById(vessel.getId());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Vessel update(Long id, VesselDTO vesselDTO) {
        // 检查船舶是否存在
        Vessel existingVessel = getById(id);
        
        // 验证船舶类型是否存在
        VesselTypeDict vesselType = vesselTypeDictService.getById(vesselDTO.getTypeId());
        
        // 验证MMSI是否与其他船舶冲突
        if (vesselMapper.countByMmsi(vesselDTO.getMmsi(), id) > 0) {
            throw new BusinessException("MMSI号码已存在: " + vesselDTO.getMmsi());
        }
        
        // 验证IMO是否与其他船舶冲突（如果提供）
        if (vesselDTO.getImo() != null && !vesselDTO.getImo().trim().isEmpty()) {
            if (vesselMapper.countByImo(vesselDTO.getImo(), id) > 0) {
                throw new BusinessException("IMO号码已存在: " + vesselDTO.getImo());
            }
        }
        
        // 更新船舶信息
        Vessel vessel = new Vessel();
        BeanUtils.copyProperties(vesselDTO, vessel);
        vessel.setId(id);
        
        int result = vesselMapper.update(vessel);
        if (result != 1) {
            throw new BusinessException("船舶更新失败");
        }
        
        log.info("船舶更新成功: {}", vessel);
        return getById(id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 检查船舶是否存在
        getById(id);
        
        // TODO: 检查是否有关联的航次或订单
        // 这里可以添加业务逻辑检查
        
        int result = vesselMapper.deleteById(id);
        if (result != 1) {
            throw new BusinessException("船舶删除失败");
        }
        
        log.info("船舶删除成功, ID: {}", id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("请选择要删除的船舶");
        }
        
        // 批量检查船舶是否存在
        for (Long id : ids) {
            getById(id);
        }
        
        // TODO: 检查是否有关联的航次或订单
        
        int result = vesselMapper.deleteBatch(ids);
        if (result != ids.size()) {
            throw new BusinessException("部分船舶删除失败");
        }
        
        log.info("批量删除船舶成功, 数量: {}", ids.size());
    }
    
    @Override
    public boolean existsByMmsi(String mmsi, Long excludeId) {
        if (mmsi == null || mmsi.trim().isEmpty()) {
            return false;
        }
        return vesselMapper.countByMmsi(mmsi, excludeId) > 0;
    }
    
    @Override
    public boolean existsByImo(String imo, Long excludeId) {
        if (imo == null || imo.trim().isEmpty()) {
            return false;
        }
        return vesselMapper.countByImo(imo, excludeId) > 0;
    }
} 