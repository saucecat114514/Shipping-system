package com.shippingsystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 船舶类型字典DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VesselTypeDictDTO {
    
    private Long id;
    
    /**
     * 类型代码
     */
    @NotBlank(message = "类型代码不能为空")
    private String typeCode;
    
    /**
     * 中文名称
     */
    @NotBlank(message = "中文名称不能为空")
    private String nameZh;
    
    /**
     * 英文名称
     */
    private String nameEn;
} 