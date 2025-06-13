package com.shippingsystem.mapper;

import com.shippingsystem.entity.Port;
import com.shippingsystem.dto.PortQueryDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 港口数据访问层接口
 */
@Mapper
public interface PortMapper {
    
    /**
     * 根据ID查询港口
     * @param id 港口ID
     * @return 港口信息
     */
    @Select("SELECT * FROM port WHERE id = #{id} AND is_deleted = 0")
    Port selectById(Long id);
    
    /**
     * 根据港口代码查询港口
     * @param portCode 港口代码
     * @return 港口信息
     */
    @Select("SELECT * FROM port WHERE port_code = #{portCode} AND is_deleted = 0")
    Port selectByPortCode(String portCode);
    
    /**
     * 分页查询港口列表
     * @param queryDTO 查询条件
     * @return 港口列表
     */
    List<Port> selectByPage(PortQueryDTO queryDTO);
    
    /**
     * 查询港口总数
     * @param queryDTO 查询条件
     * @return 总数
     */
    Long selectCount(PortQueryDTO queryDTO);
    
    /**
     * 查询所有港口（用于下拉选择）
     * @return 港口列表
     */
    @Select("SELECT * FROM port WHERE is_deleted = 0 ORDER BY name_zh")
    List<Port> selectAll();
    
    /**
     * 插入港口
     * @param port 港口信息
     * @return 影响行数
     */
    @Insert("INSERT INTO port (port_code, country_code, name_zh, name_en, longitude, latitude, created_at, updated_at, is_deleted) " +
            "VALUES (#{portCode}, #{countryCode}, #{nameZh}, #{nameEn}, #{longitude}, #{latitude}, NOW(), NOW(), 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Port port);
    
    /**
     * 更新港口
     * @param port 港口信息
     * @return 影响行数
     */
    @Update("UPDATE port SET port_code = #{portCode}, country_code = #{countryCode}, " +
            "name_zh = #{nameZh}, name_en = #{nameEn}, longitude = #{longitude}, " +
            "latitude = #{latitude}, updated_at = NOW() WHERE id = #{id} AND is_deleted = 0")
    int update(Port port);
    
    /**
     * 逻辑删除港口
     * @param id 港口ID
     * @return 影响行数
     */
    @Update("UPDATE port SET is_deleted = 1, updated_at = NOW() WHERE id = #{id}")
    int deleteById(Long id);
    
    /**
     * 批量逻辑删除港口
     * @param ids 港口ID列表
     * @return 影响行数
     */
    int deleteBatch(List<Long> ids);
    
    /**
     * 检查港口代码是否存在
     * @param portCode 港口代码
     * @param excludeId 排除的ID（用于更新时检查）
     * @return 数量
     */
    @Select("SELECT COUNT(*) FROM port WHERE port_code = #{portCode} AND is_deleted = 0 " +
            "AND (#{excludeId} IS NULL OR id != #{excludeId})")
    Long countByPortCode(@Param("portCode") String portCode, @Param("excludeId") Long excludeId);
} 