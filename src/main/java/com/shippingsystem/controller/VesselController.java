package com.shippingsystem.controller;

import com.github.pagehelper.PageInfo;
import com.shippingsystem.dto.Result;
import com.shippingsystem.dto.VesselDTO;
import com.shippingsystem.dto.VesselQueryDTO;
import com.shippingsystem.entity.Vessel;
import com.shippingsystem.service.VesselService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 船舶管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/vessels")
@RequiredArgsConstructor
public class VesselController {
    
    private final VesselService vesselService;
    
    /**
     * 根据ID查询船舶
     * @param id 船舶ID
     * @return 船舶信息
     */
    @GetMapping("/{id}")
    public Result<Vessel> getById(@PathVariable Long id) {
        log.info("查询船舶详情, ID: {}", id);
        Vessel vessel = vesselService.getById(id);
        return Result.success(vessel);
    }
    
    /**
     * 根据MMSI查询船舶
     * @param mmsi MMSI号码
     * @return 船舶信息
     */
    @GetMapping("/mmsi/{mmsi}")
    public Result<Vessel> getByMmsi(@PathVariable String mmsi) {
        log.info("根据MMSI查询船舶: {}", mmsi);
        Vessel vessel = vesselService.getByMmsi(mmsi);
        return Result.success(vessel);
    }
    
    /**
     * 根据IMO查询船舶
     * @param imo IMO号码
     * @return 船舶信息
     */
    @GetMapping("/imo/{imo}")
    public Result<Vessel> getByImo(@PathVariable String imo) {
        log.info("根据IMO查询船舶: {}", imo);
        Vessel vessel = vesselService.getByImo(imo);
        return Result.success(vessel);
    }
    
    /**
     * 分页查询船舶列表
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    @GetMapping
    public Result<PageInfo<Vessel>> getByPage(VesselQueryDTO queryDTO) {
        log.info("分页查询船舶列表: {}", queryDTO);
        PageInfo<Vessel> pageInfo = vesselService.getByPage(queryDTO);
        return Result.success(pageInfo);
    }
    
    /**
     * 获取所有可用船舶（用于下拉选择）
     * @return 船舶列表
     */
    @GetMapping("/all")
    public Result<List<Vessel>> getAllVessels() {
        log.info("获取所有可用船舶列表");
        List<Vessel> vessels = vesselService.getAllVessels();
        return Result.success(vessels);
    }
    
    /**
     * 创建船舶
     * @param vesselDTO 船舶信息
     * @return 创建的船舶
     */
    @PostMapping
    public Result<Vessel> create(@RequestBody @Validated VesselDTO vesselDTO) {
        log.info("创建船舶: {}", vesselDTO);
        Vessel vessel = vesselService.create(vesselDTO);
        return Result.success("船舶创建成功", vessel);
    }
    
    /**
     * 更新船舶
     * @param id 船舶ID
     * @param vesselDTO 船舶信息
     * @return 更新的船舶
     */
    @PutMapping("/{id}")
    public Result<Vessel> update(@PathVariable Long id, @RequestBody @Validated VesselDTO vesselDTO) {
        log.info("更新船舶, ID: {}, 数据: {}", id, vesselDTO);
        Vessel vessel = vesselService.update(id, vesselDTO);
        return Result.success("船舶更新成功", vessel);
    }
    
    /**
     * 删除船舶
     * @param id 船舶ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        log.info("删除船舶, ID: {}", id);
        vesselService.delete(id);
        return Result.success("船舶删除成功", null);
    }
    
    /**
     * 批量删除船舶
     * @param ids 船舶ID列表
     * @return 操作结果
     */
    @DeleteMapping("/batch")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        log.info("批量删除船舶, IDs: {}", ids);
        vesselService.deleteBatch(ids);
        return Result.success("船舶批量删除成功", null);
    }
    
    /**
     * 检查MMSI是否存在
     * @param mmsi MMSI号码
     * @param excludeId 排除的ID（可选，用于更新时检查）
     * @return 是否存在
     */
    @GetMapping("/check-mmsi")
    public Result<Boolean> checkMmsi(@RequestParam String mmsi, 
                                    @RequestParam(required = false) Long excludeId) {
        log.info("检查MMSI是否存在: {}, 排除ID: {}", mmsi, excludeId);
        boolean exists = vesselService.existsByMmsi(mmsi, excludeId);
        return Result.success(exists);
    }
    
    /**
     * 检查IMO是否存在
     * @param imo IMO号码
     * @param excludeId 排除的ID（可选，用于更新时检查）
     * @return 是否存在
     */
    @GetMapping("/check-imo")
    public Result<Boolean> checkImo(@RequestParam String imo, 
                                   @RequestParam(required = false) Long excludeId) {
        log.info("检查IMO是否存在: {}, 排除ID: {}", imo, excludeId);
        boolean exists = vesselService.existsByImo(imo, excludeId);
        return Result.success(exists);
    }
} 