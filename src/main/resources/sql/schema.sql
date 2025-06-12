-- 航运管理系统数据库建表脚本
-- 数据库: shipping_system
-- 字符集: utf8mb4
-- 时区: Asia/Shanghai

-- 创建数据库
CREATE DATABASE IF NOT EXISTS shipping_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE shipping_system;

-- =====================================================
-- 1. 用户权限管理表
-- =====================================================

-- 系统用户表
CREATE TABLE sys_user (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password_hash VARCHAR(255) NOT NULL COMMENT '密码哈希',
    email VARCHAR(100) COMMENT '邮箱',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 1=启用 0=禁用',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记'
) COMMENT '系统用户表';

-- 系统角色表
CREATE TABLE sys_role (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    role_code VARCHAR(30) NOT NULL UNIQUE COMMENT '角色编码: ADMIN/DISPATCHER/CUSTOMER',
    role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记'
) COMMENT '系统角色表';

-- 用户角色关联表
CREATE TABLE sys_user_role (
    user_id BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
    role_id BIGINT UNSIGNED NOT NULL COMMENT '角色ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY(user_id, role_id),
    CONSTRAINT fk_u_r_user FOREIGN KEY(user_id) REFERENCES sys_user(id) ON DELETE CASCADE,
    CONSTRAINT fk_u_r_role FOREIGN KEY(role_id) REFERENCES sys_role(id) ON DELETE CASCADE
) COMMENT '用户角色关联表';

-- =====================================================
-- 2. 港口地理信息表
-- =====================================================

-- 港口表
CREATE TABLE port (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    port_code VARCHAR(10) NOT NULL UNIQUE COMMENT 'UN/LOCODE港口代码',
    country_code CHAR(2) NOT NULL COMMENT 'ISO-3166国家代码',
    name_zh VARCHAR(100) NOT NULL COMMENT '中文名称',
    name_en VARCHAR(100) COMMENT '英文名称',
    longitude DECIMAL(10,7) NOT NULL COMMENT '经度',
    latitude DECIMAL(10,7) NOT NULL COMMENT '纬度',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记',
    INDEX idx_country_code (country_code),
    INDEX idx_location (longitude, latitude)
) COMMENT '港口表';

-- 港口多语言描述表
CREATE TABLE port_i18n (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    port_id BIGINT UNSIGNED NOT NULL COMMENT '港口ID',
    lang CHAR(5) NOT NULL COMMENT '语言代码: zh-CN/en-US',
    port_name VARCHAR(100) NOT NULL COMMENT '港口名称',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记',
    UNIQUE(port_id, lang),
    FOREIGN KEY(port_id) REFERENCES port(id) ON DELETE CASCADE
) COMMENT '港口多语言描述表';

-- =====================================================
-- 3. 船舶静态信息表
-- =====================================================

-- 船舶类型字典表
CREATE TABLE vessel_type_dict (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    type_code VARCHAR(20) NOT NULL UNIQUE COMMENT '类型编码: BULK/CONTAINER/TANKER等',
    name_zh VARCHAR(50) COMMENT '中文名称',
    name_en VARCHAR(50) COMMENT '英文名称',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记'
) COMMENT '船舶类型字典表';

-- 船舶表
CREATE TABLE vessel (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    vessel_name VARCHAR(100) NOT NULL COMMENT '船舶名称',
    mmsi VARCHAR(20) NOT NULL UNIQUE COMMENT 'MMSI海事移动业务标识',
    imo VARCHAR(20) UNIQUE COMMENT 'IMO编号',
    flag_country CHAR(2) COMMENT '船籍国',
    type_id BIGINT UNSIGNED COMMENT '船舶类型ID',
    teu_capacity INT UNSIGNED COMMENT '集装箱位',
    dwt INT UNSIGNED COMMENT '载重吨',
    service_speed DECIMAL(4,1) COMMENT '经济航速(节)',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 1=可用 2=维护 0=停用',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记',
    FOREIGN KEY(type_id) REFERENCES vessel_type_dict(id),
    INDEX idx_mmsi (mmsi),
    INDEX idx_status (status)
) COMMENT '船舶表';

-- =====================================================
-- 4. 航线与经停点表
-- =====================================================

-- 航线表
CREATE TABLE route (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    route_code VARCHAR(30) NOT NULL UNIQUE COMMENT '航线编码',
    name VARCHAR(100) COMMENT '航线名称',
    origin_port_id BIGINT UNSIGNED NOT NULL COMMENT '起始港口ID',
    dest_port_id BIGINT UNSIGNED NOT NULL COMMENT '目的港口ID',
    distance_km DECIMAL(8,1) NOT NULL COMMENT '距离(公里)',
    distance_nm DECIMAL(8,1) NOT NULL COMMENT '距离(海里)',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记',
    FOREIGN KEY(origin_port_id) REFERENCES port(id),
    FOREIGN KEY(dest_port_id) REFERENCES port(id),
    INDEX idx_route_code (route_code)
) COMMENT '航线表';

-- 航线经停点表
CREATE TABLE route_waypoint (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    route_id BIGINT UNSIGNED NOT NULL COMMENT '航线ID',
    seq_num SMALLINT UNSIGNED NOT NULL COMMENT '顺序号',
    port_id BIGINT UNSIGNED NOT NULL COMMENT '港口ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记',
    FOREIGN KEY(route_id) REFERENCES route(id) ON DELETE CASCADE,
    FOREIGN KEY(port_id) REFERENCES port(id),
    UNIQUE(route_id, seq_num)
) COMMENT '航线经停点表';

-- =====================================================
-- 5. 航次表
-- =====================================================

-- 航次表
CREATE TABLE voyage (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    voyage_code VARCHAR(40) NOT NULL UNIQUE COMMENT '航次编码',
    route_id BIGINT UNSIGNED NOT NULL COMMENT '航线ID',
    vessel_id BIGINT UNSIGNED NOT NULL COMMENT '船舶ID',
    planned_depart DATETIME NOT NULL COMMENT '计划出发时间',
    planned_arrive DATETIME NOT NULL COMMENT '计划到达时间',
    actual_depart DATETIME COMMENT '实际出发时间',
    actual_arrive DATETIME COMMENT '实际到达时间',
    distance_km DECIMAL(8,1) COMMENT '实际距离(公里)',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态: 0=待审核 1=已批准 2=进行中 3=已完成 4=已取消',
    apply_user_id BIGINT UNSIGNED COMMENT '申请人ID',
    approve_user_id BIGINT UNSIGNED COMMENT '审批人ID',
    approve_time DATETIME COMMENT '审批时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记',
    FOREIGN KEY(route_id) REFERENCES route(id),
    FOREIGN KEY(vessel_id) REFERENCES vessel(id),
    FOREIGN KEY(apply_user_id) REFERENCES sys_user(id),
    FOREIGN KEY(approve_user_id) REFERENCES sys_user(id),
    INDEX idx_voyage_code (voyage_code),
    INDEX idx_status (status),
    INDEX idx_planned_depart (planned_depart)
) COMMENT '航次表';

-- =====================================================
-- 6. 货物与订单表
-- =====================================================

-- 货物类型字典表
CREATE TABLE cargo_type_dict (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    type_code VARCHAR(30) NOT NULL UNIQUE COMMENT '类型编码: ELECTRONICS/FOOD/CHEMICAL等',
    name_zh VARCHAR(50) COMMENT '中文名称',
    name_en VARCHAR(50) COMMENT '英文名称',
    dangerous TINYINT NOT NULL DEFAULT 0 COMMENT '是否危险品: 1=是 0=否',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记'
) COMMENT '货物类型字典表';

-- 订单表
CREATE TABLE booking_order (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(40) NOT NULL UNIQUE COMMENT '订单号',
    customer_id BIGINT UNSIGNED NOT NULL COMMENT '客户ID',
    voyage_id BIGINT UNSIGNED COMMENT '航次ID',
    origin_port_id BIGINT UNSIGNED NOT NULL COMMENT '起始港口ID',
    dest_port_id BIGINT UNSIGNED NOT NULL COMMENT '目的港口ID',
    total_weight_t DECIMAL(10,2) NOT NULL COMMENT '总重量(吨)',
    total_volume_m3 DECIMAL(10,2) NOT NULL COMMENT '总体积(立方米)',
    container_teu SMALLINT UNSIGNED NOT NULL COMMENT '集装箱数量(TEU)',
    basic_freight DECIMAL(10,2) NOT NULL COMMENT '基础运费',
    extra_fee DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '附加费用',
    total_freight DECIMAL(10,2) NOT NULL COMMENT '总运费',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态: 0=待支付 1=已支付 2=运输中 3=已送达 4=取消申请 5=已取消',
    booking_time DATETIME NOT NULL COMMENT '订舱时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记',
    FOREIGN KEY(customer_id) REFERENCES sys_user(id),
    FOREIGN KEY(voyage_id) REFERENCES voyage(id),
    FOREIGN KEY(origin_port_id) REFERENCES port(id),
    FOREIGN KEY(dest_port_id) REFERENCES port(id),
    INDEX idx_order_no (order_no),
    INDEX idx_customer_id (customer_id),
    INDEX idx_status (status),
    INDEX idx_booking_time (booking_time)
) COMMENT '订单表';

-- 订单货物详情表
CREATE TABLE booking_cargo (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT UNSIGNED NOT NULL COMMENT '订单ID',
    description VARCHAR(200) COMMENT '货物描述',
    cargo_type_id BIGINT UNSIGNED COMMENT '货物类型ID',
    weight_t DECIMAL(10,2) COMMENT '重量(吨)',
    volume_m3 DECIMAL(10,2) COMMENT '体积(立方米)',
    container_qty SMALLINT UNSIGNED COMMENT '集装箱数量',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记',
    FOREIGN KEY(order_id) REFERENCES booking_order(id) ON DELETE CASCADE,
    FOREIGN KEY(cargo_type_id) REFERENCES cargo_type_dict(id)
) COMMENT '订单货物详情表';

-- 订单附加费用明细表
CREATE TABLE booking_fee_item (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT UNSIGNED NOT NULL COMMENT '订单ID',
    fee_code VARCHAR(30) NOT NULL COMMENT '费用编码: FUEL/HAZARD/SEASONAL等',
    fee_name VARCHAR(100) COMMENT '费用名称',
    amount DECIMAL(10,2) NOT NULL COMMENT '费用金额',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记',
    FOREIGN KEY(order_id) REFERENCES booking_order(id) ON DELETE CASCADE
) COMMENT '订单附加费用明细表';

-- =====================================================
-- 7. AIS动态数据表
-- =====================================================

-- AIS动态数据表
CREATE TABLE ais_dynamic (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    mmsi VARCHAR(20) NOT NULL COMMENT 'MMSI标识',
    longitude DECIMAL(10,7) NOT NULL COMMENT '经度',
    latitude DECIMAL(10,7) NOT NULL COMMENT '纬度',
    speed_kn DECIMAL(5,2) COMMENT '航速(节)',
    course_deg DECIMAL(5,1) COMMENT '航向(度)',
    timestamp_utc DATETIME NOT NULL COMMENT 'UTC时间戳',
    voyage_id BIGINT UNSIGNED COMMENT '航次ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记',
    FOREIGN KEY(voyage_id) REFERENCES voyage(id),
    INDEX idx_mmsi (mmsi),
    INDEX idx_timestamp_utc (timestamp_utc),
    INDEX idx_mmsi_time (mmsi, timestamp_utc)
) COMMENT 'AIS动态数据表';

-- =====================================================
-- 8. 系统日志表
-- =====================================================

-- 系统操作日志表
CREATE TABLE sys_operation_log (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT UNSIGNED COMMENT '操作用户ID',
    action VARCHAR(100) NOT NULL COMMENT '操作动作',
    target_id BIGINT UNSIGNED COMMENT '目标对象ID',
    target_table VARCHAR(50) COMMENT '目标表名',
    remark VARCHAR(255) COMMENT '备注',
    ip_addr VARCHAR(45) COMMENT 'IP地址',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记',
    FOREIGN KEY(user_id) REFERENCES sys_user(id),
    INDEX idx_target_table_id (target_table, target_id),
    INDEX idx_created_at (created_at)
) COMMENT '系统操作日志表';

-- 报表导出历史表
CREATE TABLE report_export_history (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    report_type VARCHAR(50) NOT NULL COMMENT '报表类型',
    exported_by BIGINT UNSIGNED NOT NULL COMMENT '导出人ID',
    exported_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '导出时间',
    params JSON COMMENT '查询参数',
    file_path VARCHAR(255) NOT NULL COMMENT '文件路径',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '软删除标记',
    FOREIGN KEY(exported_by) REFERENCES sys_user(id)
) COMMENT '报表导出历史表'; 