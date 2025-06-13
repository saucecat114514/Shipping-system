package com.shippingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 港口查询条件DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortQueryDTO {
    
    /**
     * 港口代码
     */
    private String portCode;
    
    /**
     * 国家代码
     */
    private String countryCode;
    
    /**
     * 港口名称（支持中英文模糊查询）
     */
    private String name;
    
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