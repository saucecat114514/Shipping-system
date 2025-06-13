package com.shippingsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 船舶DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VesselDTO {
    
    private Long id;
    
    /**
     * 船舶名称
     */
    @NotBlank(message = "船舶名称不能为空")
    private String vesselName;
    
    /**
     * MMSI号码 (9位数字)
     */
    @NotBlank(message = "MMSI号码不能为空")
    @Pattern(regexp = "\\d{9}", message = "MMSI号码必须是9位数字")
    private String mmsi;
    
    /**
     * IMO号码 (7位数字)
     */
    @Pattern(regexp = "\\d{7}", message = "IMO号码必须是7位数字")
    private String imo;
    
    /**
     * 船籍国家代码
     */
    private String flagCountry;
    
    /**
     * 船舶类型ID
     */
    @NotNull(message = "船舶类型不能为空")
    private Long typeId;
    
    /**
     * TEU容量
     */
    @Positive(message = "TEU容量必须大于0")
    private Integer teuCapacity;
    
    /**
     * 载重吨
     */
    @Positive(message = "载重吨必须大于0")
    private Integer dwt;
    
    /**
     * 经济航速 (节)
     */
    @Positive(message = "经济航速必须大于0")
    private BigDecimal serviceSpeed;
    
    /**
     * 船舶状态 (1:可用, 2:维护中, 3:停用)
     */
    @NotNull(message = "船舶状态不能为空")
    private Integer status;
} 