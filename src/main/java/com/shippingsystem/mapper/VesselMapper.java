package com.shippingsystem.mapper;

import com.shippingsystem.dto.VesselQueryDTO;
import com.shippingsystem.entity.Vessel;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 船舶数据访问层接口
 */
@Mapper
public interface VesselMapper {
    
    /**
     * 根据ID查询船舶（包含类型信息）
     * @param id 船舶ID
     * @return 船舶信息
     */
    Vessel selectById(Long id);
    
    /**
     * 根据MMSI查询船舶
     * @param mmsi MMSI号码
     * @return 船舶信息
     */
    @Select("SELECT * FROM vessel WHERE mmsi = #{mmsi} AND is_deleted = 0")
    Vessel selectByMmsi(String mmsi);
    
    /**
     * 根据IMO查询船舶
     * @param imo IMO号码
     * @return 船舶信息
     */
    @Select("SELECT * FROM vessel WHERE imo = #{imo} AND is_deleted = 0")
    Vessel selectByImo(String imo);
    
    /**
     * 分页查询船舶列表
     * @param queryDTO 查询条件
     * @return 船舶列表
     */
    List<Vessel> selectByPage(VesselQueryDTO queryDTO);
    
    /**
     * 查询船舶总数
     * @param queryDTO 查询条件
     * @return 总数
     */
    Long selectCount(VesselQueryDTO queryDTO);
    
    /**
     * 查询所有船舶（用于下拉选择）
     * @return 船舶列表
     */
    @Select("SELECT * FROM vessel WHERE is_deleted = 0 AND status = 1 ORDER BY vessel_name")
    List<Vessel> selectAll();
    
    /**
     * 插入船舶
     * @param vessel 船舶信息
     * @return 影响行数
     */
    @Insert("INSERT INTO vessel (vessel_name, mmsi, imo, flag_country, type_id, teu_capacity, " +
            "dwt, service_speed, status, created_at, updated_at, is_deleted) " +
            "VALUES (#{vesselName}, #{mmsi}, #{imo}, #{flagCountry}, #{typeId}, #{teuCapacity}, " +
            "#{dwt}, #{serviceSpeed}, #{status}, NOW(), NOW(), 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Vessel vessel);
    
    /**
     * 更新船舶
     * @param vessel 船舶信息
     * @return 影响行数
     */
    @Update("UPDATE vessel SET vessel_name = #{vesselName}, mmsi = #{mmsi}, imo = #{imo}, " +
            "flag_country = #{flagCountry}, type_id = #{typeId}, teu_capacity = #{teuCapacity}, " +
            "dwt = #{dwt}, service_speed = #{serviceSpeed}, status = #{status}, updated_at = NOW() " +
            "WHERE id = #{id} AND is_deleted = 0")
    int update(Vessel vessel);
    
    /**
     * 逻辑删除船舶
     * @param id 船舶ID
     * @return 影响行数
     */
    @Update("UPDATE vessel SET is_deleted = 1, updated_at = NOW() WHERE id = #{id}")
    int deleteById(Long id);
    
    /**
     * 批量逻辑删除船舶
     * @param ids 船舶ID列表
     * @return 影响行数
     */
    int deleteBatch(List<Long> ids);
    
    /**
     * 检查MMSI是否存在
     * @param mmsi MMSI号码
     * @param excludeId 排除的ID（用于更新时检查）
     * @return 数量
     */
    @Select("SELECT COUNT(*) FROM vessel WHERE mmsi = #{mmsi} AND is_deleted = 0 " +
            "AND (#{excludeId} IS NULL OR id != #{excludeId})")
    Long countByMmsi(@Param("mmsi") String mmsi, @Param("excludeId") Long excludeId);
    
    /**
     * 检查IMO是否存在
     * @param imo IMO号码
     * @param excludeId 排除的ID（用于更新时检查）
     * @return 数量
     */
    @Select("SELECT COUNT(*) FROM vessel WHERE imo = #{imo} AND is_deleted = 0 " +
            "AND (#{excludeId} IS NULL OR id != #{excludeId})")
    Long countByImo(@Param("imo") String imo, @Param("excludeId") Long excludeId);
} 