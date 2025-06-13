package com.shippingsystem.service.impl;

import com.shippingsystem.dto.VesselTypeDictDTO;
import com.shippingsystem.entity.VesselTypeDict;
import com.shippingsystem.exception.BusinessException;
import com.shippingsystem.mapper.VesselTypeDictMapper;
import com.shippingsystem.service.VesselTypeDictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 船舶类型字典服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VesselTypeDictServiceImpl implements VesselTypeDictService {
    
    private final VesselTypeDictMapper vesselTypeDictMapper;
    
    @Override
    public VesselTypeDict getById(Long id) {
        if (id == null) {
            throw new BusinessException("船舶类型ID不能为空");
        }
        VesselTypeDict vesselTypeDict = vesselTypeDictMapper.selectById(id);
        if (vesselTypeDict == null) {
            throw new BusinessException("船舶类型不存在");
        }
        return vesselTypeDict;
    }
    
    @Override
    public VesselTypeDict getByTypeCode(String typeCode) {
        if (typeCode == null || typeCode.trim().isEmpty()) {
            throw new BusinessException("船舶类型代码不能为空");
        }
        return vesselTypeDictMapper.selectByTypeCode(typeCode);
    }
    
    @Override
    public List<VesselTypeDict> getAllTypes() {
        return vesselTypeDictMapper.selectAll();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public VesselTypeDict create(VesselTypeDictDTO vesselTypeDictDTO) {
        // 验证类型代码是否已存在
        if (vesselTypeDictMapper.countByTypeCode(vesselTypeDictDTO.getTypeCode(), null) > 0) {
            throw new BusinessException("船舶类型代码已存在: " + vesselTypeDictDTO.getTypeCode());
        }
        
        // 创建船舶类型实体
        VesselTypeDict vesselTypeDict = new VesselTypeDict();
        BeanUtils.copyProperties(vesselTypeDictDTO, vesselTypeDict);
        
        // 插入数据库
        int result = vesselTypeDictMapper.insert(vesselTypeDict);
        if (result != 1) {
            throw new BusinessException("船舶类型创建失败");
        }
        
        log.info("船舶类型创建成功: {}", vesselTypeDict);
        return vesselTypeDict;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public VesselTypeDict update(Long id, VesselTypeDictDTO vesselTypeDictDTO) {
        // 检查船舶类型是否存在
        VesselTypeDict existingType = getById(id);
        
        // 验证类型代码是否与其他类型冲突
        if (vesselTypeDictMapper.countByTypeCode(vesselTypeDictDTO.getTypeCode(), id) > 0) {
            throw new BusinessException("船舶类型代码已存在: " + vesselTypeDictDTO.getTypeCode());
        }
        
        // 更新船舶类型信息
        VesselTypeDict vesselTypeDict = new VesselTypeDict();
        BeanUtils.copyProperties(vesselTypeDictDTO, vesselTypeDict);
        vesselTypeDict.setId(id);
        
        int result = vesselTypeDictMapper.update(vesselTypeDict);
        if (result != 1) {
            throw new BusinessException("船舶类型更新失败");
        }
        
        log.info("船舶类型更新成功: {}", vesselTypeDict);
        return getById(id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 检查船舶类型是否存在
        getById(id);
        
        // TODO: 检查是否有关联的船舶
        // 这里可以添加业务逻辑检查
        
        int result = vesselTypeDictMapper.deleteById(id);
        if (result != 1) {
            throw new BusinessException("船舶类型删除失败");
        }
        
        log.info("船舶类型删除成功, ID: {}", id);
    }
    
    @Override
    public boolean existsByTypeCode(String typeCode, Long excludeId) {
        if (typeCode == null || typeCode.trim().isEmpty()) {
            return false;
        }
        return vesselTypeDictMapper.countByTypeCode(typeCode, excludeId) > 0;
    }
} 