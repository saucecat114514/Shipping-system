package com.shippingsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 船舶类型字典实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VesselTypeDict {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 类型代码 (如: BULK, CONTAINER, TANKER)
     */
    private String typeCode;
    
    /**
     * 中文名称
     */
    private String nameZh;
    
    /**
     * 英文名称
     */
    private String nameEn;
    
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