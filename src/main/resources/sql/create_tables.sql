-- 港口管理模块和船舶管理模块数据库表结构

-- 1. 港口表
CREATE TABLE port (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    port_code VARCHAR(10) NOT NULL UNIQUE COMMENT '港口代码(UN/LOCODE)',
    country_code CHAR(2) NOT NULL COMMENT '国家代码(ISO-3166)',
    name_zh VARCHAR(100) NOT NULL COMMENT '港口中文名称',
    name_en VARCHAR(100) COMMENT '港口英文名称',
    longitude DECIMAL(10,7) NOT NULL COMMENT '经度',
    latitude DECIMAL(10,7) NOT NULL COMMENT '纬度',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标记(0:未删除, 1:已删除)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='港口表';

-- 2. 船舶类型字典表
CREATE TABLE vessel_type_dict (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    type_code VARCHAR(20) NOT NULL UNIQUE COMMENT '类型代码(如:BULK,CONTAINER,TANKER)',
    name_zh VARCHAR(50) COMMENT '中文名称',
    name_en VARCHAR(50) COMMENT '英文名称',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标记(0:未删除, 1:已删除)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='船舶类型字典表';

-- 3. 船舶表
CREATE TABLE vessel (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    vessel_name VARCHAR(100) NOT NULL COMMENT '船舶名称',
    mmsi VARCHAR(20) NOT NULL UNIQUE COMMENT 'MMSI号码(海事移动业务标识)',
    imo VARCHAR(20) UNIQUE COMMENT 'IMO号码(国际海事组织编号)',
    flag_country CHAR(2) COMMENT '船籍国家代码',
    type_id BIGINT UNSIGNED COMMENT '船舶类型ID(关联vessel_type_dict表)',
    teu_capacity INT UNSIGNED COMMENT 'TEU容量(标准集装箱位)',
    dwt INT UNSIGNED COMMENT '载重吨(DWT)',
    service_speed DECIMAL(4,1) COMMENT '经济航速(节)',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '船舶状态(1:可用, 2:维护中, 3:停用)',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标记(0:未删除, 1:已删除)',
    INDEX idx_mmsi (mmsi),
    INDEX idx_imo (imo),
    INDEX idx_type_id (type_id),
    INDEX idx_status (status),
    FOREIGN KEY (type_id) REFERENCES vessel_type_dict(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='船舶表';

-- 插入默认船舶类型数据
INSERT INTO vessel_type_dict (type_code, name_zh, name_en) VALUES
('BULK', '散货船', 'Bulk Carrier'),
('CONTAINER', '集装箱船', 'Container Ship'),
('TANKER', '油轮', 'Tanker'),
('GENERAL', '杂货船', 'General Cargo Ship'),
('RORO', '滚装船', 'Roll-on/Roll-off Ship'),
('PASSENGER', '客船', 'Passenger Ship'),
('REEFER', '冷藏船', 'Reefer Ship'),
('LNG', '液化天然气船', 'LNG Carrier'),
('LPG', '液化石油气船', 'LPG Carrier'),
('CHEMICAL', '化学品船', 'Chemical Tanker');

-- 插入示例港口数据
INSERT INTO port (port_code, country_code, name_zh, name_en, longitude, latitude) VALUES
('CNSHA', 'CN', '上海港', 'Shanghai Port', 121.4737, 31.2304),
('CNSZX', 'CN', '深圳港', 'Shenzhen Port', 114.0579, 22.5431),
('CNNGB', 'CN', '宁波舟山港', 'Ningbo-Zhoushan Port', 121.5440, 29.8658),
('CNQIN', 'CN', '青岛港', 'Qingdao Port', 120.3826, 36.0986),
('CNTXG', 'CN', '天津港', 'Tianjin Port', 117.7011, 39.0458),
('SGSIN', 'SG', '新加坡港', 'Port of Singapore', 103.8198, 1.3521),
('USNYC', 'US', '纽约港', 'Port of New York', -74.0059, 40.7128),
('USLAX', 'US', '洛杉矶港', 'Port of Los Angeles', -118.2437, 33.7701),
('NLRTM', 'NL', '鹿特丹港', 'Port of Rotterdam', 4.4777, 51.9244),
('DEHAM', 'DE', '汉堡港', 'Port of Hamburg', 9.9937, 53.5511);

-- 插入示例船舶数据
INSERT INTO vessel (vessel_name, mmsi, imo, flag_country, type_id, teu_capacity, dwt, service_speed, status) VALUES
('COSCO SHIPPING UNIVERSE', '477123456', '9795488', 'CN', 2, 21413, 199000, 22.5, 1),
('MAERSK MADRID', '219123456', '9778693', 'DK', 2, 20568, 190000, 23.0, 1),
('EVER GIVEN', '353123456', '9811000', 'PA', 2, 20124, 199629, 22.8, 1),
('SEAWISE GIANT', '477987654', '7381154', 'CN', 3, NULL, 564763, 16.5, 3),
('VALE BEIJING', '477456789', '9570162', 'CN', 1, NULL, 402347, 14.8, 1);

-- 创建索引以提高查询性能
CREATE INDEX idx_port_code ON port(port_code);
CREATE INDEX idx_port_name_zh ON port(name_zh);
CREATE INDEX idx_port_country ON port(country_code);
CREATE INDEX idx_vessel_name ON vessel(vessel_name);
CREATE INDEX idx_vessel_type_code ON vessel_type_dict(type_code); 