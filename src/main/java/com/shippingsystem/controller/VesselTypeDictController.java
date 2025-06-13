package com.shippingsystem.controller;

import com.shippingsystem.dto.Result;
import com.shippingsystem.dto.VesselTypeDictDTO;
import com.shippingsystem.entity.VesselTypeDict;
import com.shippingsystem.service.VesselTypeDictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 船舶类型字典控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/vessel-types")
@RequiredArgsConstructor
public class VesselTypeDictController {
    
    private final VesselTypeDictService vesselTypeDictService;
    
    /**
     * 根据ID查询船舶类型
     * @param id 类型ID
     * @return 船舶类型信息
     */
    @GetMapping("/{id}")
    public Result<VesselTypeDict> getById(@PathVariable Long id) {
        log.info("查询船舶类型详情, ID: {}", id);
        VesselTypeDict vesselTypeDict = vesselTypeDictService.getById(id);
        return Result.success(vesselTypeDict);
    }
    
    /**
     * 获取所有船舶类型
     * @return 船舶类型列表
     */
    @GetMapping
    public Result<List<VesselTypeDict>> getAllTypes() {
        log.info("获取所有船舶类型列表");
        List<VesselTypeDict> types = vesselTypeDictService.getAllTypes();
        return Result.success(types);
    }
    
    /**
     * 创建船舶类型
     * @param vesselTypeDictDTO 船舶类型信息
     * @return 创建的船舶类型
     */
    @PostMapping
    public Result<VesselTypeDict> create(@RequestBody @Validated VesselTypeDictDTO vesselTypeDictDTO) {
        log.info("创建船舶类型: {}", vesselTypeDictDTO);
        VesselTypeDict vesselTypeDict = vesselTypeDictService.create(vesselTypeDictDTO);
        return Result.success("船舶类型创建成功", vesselTypeDict);
    }
    
    /**
     * 更新船舶类型
     * @param id 类型ID
     * @param vesselTypeDictDTO 船舶类型信息
     * @return 更新的船舶类型
     */
    @PutMapping("/{id}")
    public Result<VesselTypeDict> update(@PathVariable Long id, 
                                        @RequestBody @Validated VesselTypeDictDTO vesselTypeDictDTO) {
        log.info("更新船舶类型, ID: {}, 数据: {}", id, vesselTypeDictDTO);
        VesselTypeDict vesselTypeDict = vesselTypeDictService.update(id, vesselTypeDictDTO);
        return Result.success("船舶类型更新成功", vesselTypeDict);
    }
    
    /**
     * 删除船舶类型
     * @param id 类型ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        log.info("删除船舶类型, ID: {}", id);
        vesselTypeDictService.delete(id);
        return Result.success("船舶类型删除成功", null);
    }
    
    /**
     * 检查类型代码是否存在
     * @param typeCode 类型代码
     * @param excludeId 排除的ID（可选，用于更新时检查）
     * @return 是否存在
     */
    @GetMapping("/check-code")
    public Result<Boolean> checkTypeCode(@RequestParam String typeCode, 
                                        @RequestParam(required = false) Long excludeId) {
        log.info("检查船舶类型代码是否存在: {}, 排除ID: {}", typeCode, excludeId);
        boolean exists = vesselTypeDictService.existsByTypeCode(typeCode, excludeId);
        return Result.success(exists);
    }
} 