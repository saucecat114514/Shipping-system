package com.shippingsystem.controller;

import com.github.pagehelper.PageInfo;
import com.shippingsystem.dto.PortDTO;
import com.shippingsystem.dto.PortQueryDTO;
import com.shippingsystem.dto.Result;
import com.shippingsystem.entity.Port;
import com.shippingsystem.service.PortService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 港口管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/ports")
@RequiredArgsConstructor
public class PortController {
    
    private final PortService portService;
    
    /**
     * 根据ID查询港口
     * @param id 港口ID
     * @return 港口信息
     */
    @GetMapping("/{id}")
    public Result<Port> getById(@PathVariable Long id) {
        log.info("查询港口详情, ID: {}", id);
        Port port = portService.getById(id);
        return Result.success(port);
    }
    
    /**
     * 分页查询港口列表
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    @GetMapping
    public Result<PageInfo<Port>> getByPage(PortQueryDTO queryDTO) {
        log.info("分页查询港口列表: {}", queryDTO);
        PageInfo<Port> pageInfo = portService.getByPage(queryDTO);
        return Result.success(pageInfo);
    }
    
    /**
     * 获取所有港口（用于下拉选择）
     * @return 港口列表
     */
    @GetMapping("/all")
    public Result<List<Port>> getAllPorts() {
        log.info("获取所有港口列表");
        List<Port> ports = portService.getAllPorts();
        return Result.success(ports);
    }
    
    /**
     * 创建港口
     * @param portDTO 港口信息
     * @return 创建的港口
     */
    @PostMapping
    public Result<Port> create(@RequestBody @Validated PortDTO portDTO) {
        log.info("创建港口: {}", portDTO);
        Port port = portService.create(portDTO);
        return Result.success("港口创建成功", port);
    }
    
    /**
     * 更新港口
     * @param id 港口ID
     * @param portDTO 港口信息
     * @return 更新的港口
     */
    @PutMapping("/{id}")
    public Result<Port> update(@PathVariable Long id, @RequestBody @Validated PortDTO portDTO) {
        log.info("更新港口, ID: {}, 数据: {}", id, portDTO);
        Port port = portService.update(id, portDTO);
        return Result.success("港口更新成功", port);
    }
    
    /**
     * 删除港口
     * @param id 港口ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        log.info("删除港口, ID: {}", id);
        portService.delete(id);
        return Result.success("港口删除成功", null);
    }
    
    /**
     * 批量删除港口
     * @param ids 港口ID列表
     * @return 操作结果
     */
    @DeleteMapping("/batch")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        log.info("批量删除港口, IDs: {}", ids);
        portService.deleteBatch(ids);
        return Result.success("港口批量删除成功", null);
    }
    
    /**
     * 检查港口代码是否存在
     * @param portCode 港口代码
     * @param excludeId 排除的ID（可选，用于更新时检查）
     * @return 是否存在
     */
    @GetMapping("/check-code")
    public Result<Boolean> checkPortCode(@RequestParam String portCode, 
                                        @RequestParam(required = false) Long excludeId) {
        log.info("检查港口代码是否存在: {}, 排除ID: {}", portCode, excludeId);
        boolean exists = portService.existsByPortCode(portCode, excludeId);
        return Result.success(exists);
    }
} 