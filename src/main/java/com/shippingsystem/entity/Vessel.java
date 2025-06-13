package com.shippingsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 船舶实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vessel {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 船舶名称
     */
    private String vesselName;
    
    /**
     * MMSI号码 (海事移动业务标识)
     */
    private String mmsi;
    
    /**
     * IMO号码 (国际海事组织编号)
     */
    private String imo;
    
    /**
     * 船籍国家代码
     */
    private String flagCountry;
    
    /**
     * 船舶类型ID (关联vessel_type_dict表)
     */
    private Long typeId;
    
    /**
     * 船舶类型信息 (关联查询结果)
     */
    private VesselTypeDict vesselType;
    
    /**
     * TEU容量 (标准集装箱位)
     */
    private Integer teuCapacity;
    
    /**
     * 载重吨 (DWT)
     */
    private Integer dwt;
    
    /**
     * 经济航速 (节)
     */
    private BigDecimal serviceSpeed;
    
    /**
     * 船舶状态 (1:可用, 2:维护中, 3:停用)
     */
    private Integer status;
    
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