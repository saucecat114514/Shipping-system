package com.shippingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 船舶查询条件DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VesselQueryDTO {
    
    /**
     * 船舶名称（模糊查询）
     */
    private String vesselName;
    
    /**
     * MMSI号码
     */
    private String mmsi;
    
    /**
     * IMO号码
     */
    private String imo;
    
    /**
     * 船籍国家代码
     */
    private String flagCountry;
    
    /**
     * 船舶类型ID
     */
    private Long typeId;
    
    /**
     * 船舶状态
     */
    private Integer status;
    
    /**
     * 页码
     */
    private Integer pageNum;
    
    /**
     * 每页数量
     */
    private Integer pageSize;
    
    /**
     * 排序字段
     */
    private String orderBy;
    
    /**
     * 排序方向 (asc/desc)
     */
    private String orderDir;
} 