package com.shippingsystem.mapper;

import com.shippingsystem.entity.VesselTypeDict;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 船舶类型字典数据访问层接口
 */
@Mapper
public interface VesselTypeDictMapper {
    
    /**
     * 根据ID查询船舶类型
     * @param id 类型ID
     * @return 船舶类型信息
     */
    @Select("SELECT * FROM vessel_type_dict WHERE id = #{id} AND is_deleted = 0")
    VesselTypeDict selectById(Long id);
    
    /**
     * 根据类型代码查询船舶类型
     * @param typeCode 类型代码
     * @return 船舶类型信息
     */
    @Select("SELECT * FROM vessel_type_dict WHERE type_code = #{typeCode} AND is_deleted = 0")
    VesselTypeDict selectByTypeCode(String typeCode);
    
    /**
     * 查询所有船舶类型
     * @return 船舶类型列表
     */
    @Select("SELECT * FROM vessel_type_dict WHERE is_deleted = 0 ORDER BY type_code")
    List<VesselTypeDict> selectAll();
    
    /**
     * 插入船舶类型
     * @param vesselTypeDict 船舶类型信息
     * @return 影响行数
     */
    @Insert("INSERT INTO vessel_type_dict (type_code, name_zh, name_en, created_at, updated_at, is_deleted) " +
            "VALUES (#{typeCode}, #{nameZh}, #{nameEn}, NOW(), NOW(), 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(VesselTypeDict vesselTypeDict);
    
    /**
     * 更新船舶类型
     * @param vesselTypeDict 船舶类型信息
     * @return 影响行数
     */
    @Update("UPDATE vessel_type_dict SET type_code = #{typeCode}, name_zh = #{nameZh}, " +
            "name_en = #{nameEn}, updated_at = NOW() WHERE id = #{id} AND is_deleted = 0")
    int update(VesselTypeDict vesselTypeDict);
    
    /**
     * 逻辑删除船舶类型
     * @param id 类型ID
     * @return 影响行数
     */
    @Update("UPDATE vessel_type_dict SET is_deleted = 1, updated_at = NOW() WHERE id = #{id}")
    int deleteById(Long id);
    
    /**
     * 检查类型代码是否存在
     * @param typeCode 类型代码
     * @param excludeId 排除的ID（用于更新时检查）
     * @return 数量
     */
    @Select("SELECT COUNT(*) FROM vessel_type_dict WHERE type_code = #{typeCode} AND is_deleted = 0 " +
            "AND (#{excludeId} IS NULL OR id != #{excludeId})")
    Long countByTypeCode(@Param("typeCode") String typeCode, @Param("excludeId") Long excludeId);
} 