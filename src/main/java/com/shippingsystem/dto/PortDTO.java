package com.shippingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 港口数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortDTO {
    
    private Long id;
    
    /**
     * 港口代码 (UN/LOCODE)
     */
    @NotBlank(message = "港口代码不能为空")
    private String portCode;
    
    /**
     * 国家代码 (ISO-3166)
     */
    @NotBlank(message = "国家代码不能为空")
    private String countryCode;
    
    /**
     * 港口中文名称
     */
    @NotBlank(message = "港口中文名称不能为空")
    private String nameZh;
    
    /**
     * 港口英文名称
     */
    private String nameEn;
    
    /**
     * 经度 (-180 ~ 180)
     */
    @NotNull(message = "经度不能为空")
    @DecimalMin(value = "-180.0", message = "经度范围为-180到180")
    @DecimalMax(value = "180.0", message = "经度范围为-180到180")
    private BigDecimal longitude;
    
    /**
     * 纬度 (-90 ~ 90)
     */
    @NotNull(message = "纬度不能为空")
    @DecimalMin(value = "-90.0", message = "纬度范围为-90到90")
    @DecimalMax(value = "90.0", message = "纬度范围为-90到90")
    private BigDecimal latitude;
} 