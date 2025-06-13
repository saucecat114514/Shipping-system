package com.shippingsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shippingsystem.dto.PortDTO;
import com.shippingsystem.dto.PortQueryDTO;
import com.shippingsystem.entity.Port;
import com.shippingsystem.exception.BusinessException;
import com.shippingsystem.mapper.PortMapper;
import com.shippingsystem.service.PortService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 港口服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PortServiceImpl implements PortService {
    
    private final PortMapper portMapper;
    
    @Override
    public Port getById(Long id) {
        if (id == null) {
            throw new BusinessException("港口ID不能为空");
        }
        Port port = portMapper.selectById(id);
        if (port == null) {
            throw new BusinessException("港口不存在");
        }
        return port;
    }
    
    @Override
    public Port getByPortCode(String portCode) {
        if (portCode == null || portCode.trim().isEmpty()) {
            throw new BusinessException("港口代码不能为空");
        }
        return portMapper.selectByPortCode(portCode);
    }
    
    @Override
    public PageInfo<Port> getByPage(PortQueryDTO queryDTO) {
        // 设置默认分页参数
        if (queryDTO.getPageNum() == null || queryDTO.getPageNum() <= 0) {
            queryDTO.setPageNum(1);
        }
        if (queryDTO.getPageSize() == null || queryDTO.getPageSize() <= 0) {
            queryDTO.setPageSize(10);
        }
        
        // 开始分页
        PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
        List<Port> ports = portMapper.selectByPage(queryDTO);
        return new PageInfo<>(ports);
    }
    
    @Override
    public List<Port> getAllPorts() {
        return portMapper.selectAll();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Port create(PortDTO portDTO) {
        // 验证港口代码是否已存在
        if (portMapper.countByPortCode(portDTO.getPortCode(), null) > 0) {
            throw new BusinessException("港口代码已存在: " + portDTO.getPortCode());
        }
        
        // 创建港口实体
        Port port = new Port();
        BeanUtils.copyProperties(portDTO, port);
        
        // 插入数据库
        int result = portMapper.insert(port);
        if (result != 1) {
            throw new BusinessException("港口创建失败");
        }
        
        log.info("港口创建成功: {}", port);
        return port;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Port update(Long id, PortDTO portDTO) {
        // 检查港口是否存在
        Port existingPort = getById(id);
        
        // 验证港口代码是否与其他港口冲突
        if (portMapper.countByPortCode(portDTO.getPortCode(), id) > 0) {
            throw new BusinessException("港口代码已存在: " + portDTO.getPortCode());
        }
        
        // 更新港口信息
        Port port = new Port();
        BeanUtils.copyProperties(portDTO, port);
        port.setId(id);
        
        int result = portMapper.update(port);
        if (result != 1) {
            throw new BusinessException("港口更新失败");
        }
        
        log.info("港口更新成功: {}", port);
        return getById(id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 检查港口是否存在
        getById(id);
        
        // TODO: 检查是否有关联的航线、航次或订单
        // 这里可以添加业务逻辑检查
        
        int result = portMapper.deleteById(id);
        if (result != 1) {
            throw new BusinessException("港口删除失败");
        }
        
        log.info("港口删除成功, ID: {}", id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("请选择要删除的港口");
        }
        
        // 批量检查港口是否存在
        for (Long id : ids) {
            getById(id);
        }
        
        // TODO: 检查是否有关联的航线、航次或订单
        
        int result = portMapper.deleteBatch(ids);
        if (result != ids.size()) {
            throw new BusinessException("部分港口删除失败");
        }
        
        log.info("批量删除港口成功, 数量: {}", ids.size());
    }
    
    @Override
    public boolean existsByPortCode(String portCode, Long excludeId) {
        if (portCode == null || portCode.trim().isEmpty()) {
            return false;
        }
        return portMapper.countByPortCode(portCode, excludeId) > 0;
    }
} 