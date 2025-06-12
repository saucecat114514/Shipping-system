-- 航运管理系统基础数据初始化脚本
USE shipping_system;

-- =====================================================
-- 1. 系统角色初始化数据
-- =====================================================

INSERT INTO sys_role (role_code, role_name) VALUES 
('ADMIN', '系统管理员'),
('DISPATCHER', '航运调度员'),
('CUSTOMER', '客户');

-- =====================================================
-- 2. 默认管理员用户 (密码: admin123)
-- =====================================================

INSERT INTO sys_user (username, password_hash, email, status) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'admin@shippingsystem.com', 1);

-- 为管理员分配角色
INSERT INTO sys_user_role (user_id, role_id) VALUES 
(1, 1);

-- =====================================================
-- 3. 船舶类型字典数据
-- =====================================================

INSERT INTO vessel_type_dict (type_code, name_zh, name_en) VALUES 
('BULK', '散货船', 'Bulk Carrier'),
('CONTAINER', '集装箱船', 'Container Ship'),
('TANKER', '油轮', 'Tanker'),
('GENERAL', '杂货船', 'General Cargo Ship'),
('REEFER', '冷藏船', 'Reefer Ship'),
('RO_RO', '滚装船', 'Roll-on/Roll-off Ship'),
('LNG', 'LNG船', 'LNG Carrier'),
('CHEMICAL', '化学品船', 'Chemical Tanker');

-- =====================================================
-- 4. 货物类型字典数据
-- =====================================================

INSERT INTO cargo_type_dict (type_code, name_zh, name_en, dangerous) VALUES 
('ELECTRONICS', '电子产品', 'Electronics', 0),
('FOOD', '食品', 'Food Products', 0),
('CLOTHING', '服装', 'Clothing & Textiles', 0),
('MACHINERY', '机械设备', 'Machinery', 0),
('CHEMICALS', '化学品', 'Chemicals', 1),
('PETROLEUM', '石油产品', 'Petroleum Products', 1),
('STEEL', '钢铁', 'Steel Products', 0),
('GRAIN', '粮食', 'Grain', 0),
('COAL', '煤炭', 'Coal', 0),
('ORE', '矿石', 'Ore', 0),
('AUTOMOBILES', '汽车', 'Automobiles', 0),
('FROZEN_GOODS', '冷冻货物', 'Frozen Goods', 0);

-- =====================================================
-- 5. 示例港口数据 (主要亚洲港口)
-- =====================================================

INSERT INTO port (port_code, country_code, name_zh, name_en, longitude, latitude) VALUES 
-- 中国港口
('CNSHA', 'CN', '上海港', 'Shanghai', 121.4737, 31.2304),
('CNNGB', 'CN', '宁波舟山港', 'Ningbo-Zhoushan', 121.5540, 29.8683),
('CNSZX', 'CN', '深圳港', 'Shenzhen', 113.9547, 22.5274),
('CNQIN', 'CN', '青岛港', 'Qingdao', 120.3826, 36.0671),
('CNTXG', 'CN', '天津港', 'Tianjin', 117.2008, 39.1042),
('CNXMN', 'CN', '厦门港', 'Xiamen', 118.0894, 24.4798),
('CNGZH', 'CN', '广州港', 'Guangzhou', 113.2644, 23.1291),
('CNDLC', 'CN', '大连港', 'Dalian', 121.6147, 38.9140),

-- 新加坡
('SGSIN', 'SG', '新加坡港', 'Singapore', 103.8198, 1.3521),

-- 日本港口
('JPTYO', 'JP', '东京港', 'Tokyo', 139.6917, 35.6895),
('JPYOK', 'JP', '横滨港', 'Yokohama', 139.6380, 35.4437),
('JPKOB', 'JP', '神户港', 'Kobe', 135.1955, 34.6901),
('JPNGO', 'JP', '名古屋港', 'Nagoya', 136.9066, 35.1815),

-- 韩国港口
('KRPUS', 'KR', '釜山港', 'Busan', 129.0756, 35.1796),
('KRICN', 'KR', '仁川港', 'Incheon', 126.7052, 37.4563),

-- 香港
('HKHKG', 'HK', '香港港', 'Hong Kong', 114.1694, 22.3193),

-- 台湾
('TWKHH', 'TW', '高雄港', 'Kaohsiung', 120.3014, 22.6273),
('TWKEL', 'TW', '基隆港', 'Keelung', 121.7390, 25.1276),

-- 马来西亚
('MYPKG', 'MY', '巴生港', 'Port Klang', 101.3933, 3.0048),
('MYJHB', 'MY', '新山港', 'Johor Bahru', 103.7414, 1.4927),

-- 泰国
('THBKK', 'TH', '曼谷港', 'Bangkok', 100.5018, 13.7563),
('THLCH', 'TH', '林查班港', 'Laem Chabang', 100.8830, 13.0827),

-- 越南
('VNSGN', 'VN', '胡志明市港', 'Ho Chi Minh City', 106.6297, 10.8231),
('VNHAN', 'VN', '海防港', 'Haiphong', 106.6881, 20.8648),

-- 印度尼西亚
('IDJKT', 'ID', '雅加达港', 'Jakarta', 106.8456, -6.1944),
('IDSRG', 'ID', '三宝垄港', 'Semarang', 110.4203, -6.9889),

-- 菲律宾
('PHMNL', 'PH', '马尼拉港', 'Manila', 120.9842, 14.5995),
('PHCEB', 'PH', '宿务港', 'Cebu', 123.8854, 10.2349);

-- =====================================================
-- 6. 示例船舶数据
-- =====================================================

INSERT INTO vessel (vessel_name, mmsi, imo, flag_country, type_id, teu_capacity, dwt, service_speed, status) VALUES 
('东方明珠号', '412345678', 'IMO1234567', 'CN', 2, 20000, 200000, 22.5, 1),
('海上丝路号', '412345679', 'IMO1234568', 'CN', 2, 18000, 180000, 21.0, 1),
('蓝海先锋号', '412345680', 'IMO1234569', 'CN', 1, 0, 300000, 15.0, 1),
('远洋巨人号', '563456789', 'IMO2345678', 'SG', 2, 22000, 220000, 23.0, 1),
('亚洲之星号', '431987654', 'IMO3456789', 'JP', 2, 14000, 140000, 20.5, 1);

-- =====================================================
-- 7. 示例航线数据
-- =====================================================

INSERT INTO route (route_code, name, origin_port_id, dest_port_id, distance_km, distance_nm) VALUES 
('SHA-SIN-001', '上海-新加坡航线', 1, 9, 4630.0, 2500.0),
('SHA-TOK-001', '上海-东京航线', 1, 10, 1800.0, 972.0),
('SIN-HKG-001', '新加坡-香港航线', 9, 17, 2590.0, 1400.0),
('NGB-PUS-001', '宁波-釜山航线', 2, 15, 1200.0, 648.0),
('SZX-MNL-001', '深圳-马尼拉航线', 3, 29, 1850.0, 1000.0);

-- =====================================================
-- 8. 测试用户数据
-- =====================================================

-- 创建调度员用户 (密码: dispatcher123)
INSERT INTO sys_user (username, password_hash, email, status) VALUES 
('dispatcher01', '$2a$10$rZ8R5o8E6n7l2VmA4X8XO.J3l4V2R5T8X6M9A2s5P7R9E3N4V6B8', 'dispatcher01@shippingsystem.com', 1);

-- 为调度员分配角色
INSERT INTO sys_user_role (user_id, role_id) VALUES 
(2, 2);

-- 创建客户用户 (密码: customer123)
INSERT INTO sys_user (username, password_hash, email, status) VALUES 
('customer01', '$2a$10$sA9S6p9F7o8m3WnB5Y9YP.K4m5W3S6U9Y7N0B3t6Q8S0F4O5W7C9', 'customer01@shippingsystem.com', 1),
('customer02', '$2a$10$tB0T7q0G8p9n4XoC6Z0ZQ.L5n6X4T7V0Z8O1C4u7R9T1G5P6X8D0', 'customer02@shippingsystem.com', 1);

-- 为客户分配角色
INSERT INTO sys_user_role (user_id, role_id) VALUES 
(3, 3),
(4, 3);

-- =====================================================
-- 9. 示例AIS数据 (用于测试)
-- =====================================================

INSERT INTO ais_dynamic (mmsi, longitude, latitude, speed_kn, course_deg, timestamp_utc) VALUES 
('412345678', 121.4737, 31.2304, 0.0, 0.0, '2024-01-01 00:00:00'),
('412345678', 121.5000, 31.2500, 15.5, 95.0, '2024-01-01 01:00:00'),
('412345678', 121.5500, 31.2800, 16.2, 100.0, '2024-01-01 02:00:00'),
('412345679', 121.5540, 29.8683, 0.0, 0.0, '2024-01-01 00:00:00'),
('412345679', 121.6000, 29.9000, 18.0, 180.0, '2024-01-01 01:00:00'),
('412345680', 121.4737, 31.2304, 12.5, 45.0, '2024-01-01 00:00:00');

-- =====================================================
-- 提交事务
-- =====================================================
COMMIT; 