package com.shippingsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 港口实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Port {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 港口代码 (UN/LOCODE)
     */
    private String portCode;
    
    /**
     * 国家代码 (ISO-3166)
     */
    private String countryCode;
    
    /**
     * 港口中文名称
     */
    private String nameZh;
    
    /**
     * 港口英文名称
     */
    private String nameEn;
    
    /**
     * 经度
     */
    private BigDecimal longitude;
    
    /**
     * 纬度
     */
    private BigDecimal latitude;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    
    /**
     * 逻辑删除标记 (0:未删除, 1:已删除)
     */
    private Integer isDeleted;
} 