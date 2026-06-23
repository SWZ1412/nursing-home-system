-- ============================================
-- 养老院管理信息系统 - MySQL 5.5 兼容版
-- ============================================

CREATE DATABASE IF NOT EXISTS nursing_home DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE nursing_home;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(100) NOT NULL,
    `real_name` VARCHAR(50),
    `role` VARCHAR(20) DEFAULT 'NURSE',
    `phone` VARCHAR(20),
    `email` VARCHAR(100),
    `status` TINYINT DEFAULT 1,
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 房间表
CREATE TABLE IF NOT EXISTS `room` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `room_no` VARCHAR(20) NOT NULL UNIQUE,
    `building` VARCHAR(50),
    `floor` INT,
    `room_type` VARCHAR(20),
    `capacity` INT DEFAULT 1,
    `occupied` INT DEFAULT 0,
    `price` DECIMAL(10,2),
    `status` VARCHAR(20) DEFAULT 'AVAILABLE',
    `description` VARCHAR(255),
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 老人表
CREATE TABLE IF NOT EXISTS `elder` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `elder_no` VARCHAR(30) NOT NULL UNIQUE,
    `name` VARCHAR(50) NOT NULL,
    `gender` VARCHAR(10),
    `birthday` DATE,
    `id_card` VARCHAR(20),
    `phone` VARCHAR(20),
    `emergency_contact` VARCHAR(50),
    `emergency_phone` VARCHAR(20),
    `address` VARCHAR(200),
    `admission_date` DATE,
    `health_status` VARCHAR(50),
    `room_id` INT,
    `bed_no` VARCHAR(10),
    `status` VARCHAR(20) DEFAULT 'ACTIVE',
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 健康记录表
CREATE TABLE IF NOT EXISTS `health_record` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `elder_id` INT NOT NULL,
    `record_date` DATE NOT NULL,
    `temperature` DECIMAL(4,1),
    `blood_pressure_systolic` INT,
    `blood_pressure_diastolic` INT,
    `blood_sugar` DECIMAL(5,1),
    `heart_rate` INT,
    `medication` VARCHAR(500),
    `nurse_note` VARCHAR(1000),
    `nurse_id` INT,
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 药品表
CREATE TABLE IF NOT EXISTS `medicine` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `medicine_no` VARCHAR(30) NOT NULL UNIQUE,
    `name` VARCHAR(100) NOT NULL,
    `category` VARCHAR(50),
    `specification` VARCHAR(100),
    `unit` VARCHAR(20),
    `price` DECIMAL(10,2),
    `stock` INT DEFAULT 0,
    `min_stock` INT DEFAULT 10,
    `manufacturer` VARCHAR(200),
    `expiry_date` DATE,
    `description` VARCHAR(500),
    `status` TINYINT DEFAULT 1,
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用药计划表
CREATE TABLE IF NOT EXISTS `medication_plan` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `elder_id` INT NOT NULL,
    `medicine_id` INT NOT NULL,
    `dosage` VARCHAR(50),
    `frequency` VARCHAR(100),
    `start_date` DATE,
    `end_date` DATE,
    `remark` VARCHAR(500),
    `status` VARCHAR(20) DEFAULT 'ACTIVE',
    `doctor_id` INT,
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 账单表
CREATE TABLE IF NOT EXISTS `bill` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `bill_no` VARCHAR(30) NOT NULL UNIQUE,
    `elder_id` INT NOT NULL,
    `bill_type` VARCHAR(50),
    `amount` DECIMAL(10,2) DEFAULT 0,
    `paid_amount` DECIMAL(10,2) DEFAULT 0,
    `status` VARCHAR(20) DEFAULT 'UNPAID',
    `bill_month` VARCHAR(10),
    `due_date` DATE,
    `remark` VARCHAR(500),
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 缴费记录表
CREATE TABLE IF NOT EXISTS `payment` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `payment_no` VARCHAR(30) NOT NULL UNIQUE,
    `bill_id` INT,
    `elder_id` INT NOT NULL,
    `amount` DECIMAL(10,2) DEFAULT 0,
    `payment_method` VARCHAR(20),
    `payment_time` TIMESTAMP NULL,
    `operator_id` INT,
    `remark` VARCHAR(500),
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 访客记录表
CREATE TABLE IF NOT EXISTS `visitor_record` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `visitor_name` VARCHAR(50) NOT NULL,
    `id_card` VARCHAR(20),
    `phone` VARCHAR(20),
    `elder_id` INT NOT NULL,
    `visit_time` TIMESTAMP NULL,
    `leave_time` TIMESTAMP NULL,
    `purpose` VARCHAR(200),
    `remark` VARCHAR(500),
    `operator_id` INT,
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 事故记录表
CREATE TABLE IF NOT EXISTS `accident_record` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `accident_no` VARCHAR(30) NOT NULL UNIQUE,
    `elder_id` INT NOT NULL,
    `employee_id` INT,
    `accident_type` VARCHAR(50),
    `accident_time` TIMESTAMP NULL,
    `location` VARCHAR(100),
    `description` VARCHAR(1000),
    `severity` VARCHAR(20),
    `handling_result` VARCHAR(1000),
    `reporter_id` INT,
    `report_time` TIMESTAMP NULL,
    `status` VARCHAR(20) DEFAULT 'REPORTED'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ============================================
-- 初始数据
-- ============================================

INSERT INTO `user` (`username`, `password`, `real_name`, `role`, `phone`, `email`, `status`)
VALUES ('admin', MD5('admin123'), '系统管理员', 'ADMIN', '13800000000', 'admin@nursinghome.com', 1);

INSERT INTO `room` (`room_no`, `building`, `floor`, `room_type`, `capacity`, `price`, `status`, `description`) VALUES
('A101', 'A栋', 1, '单人间', 1, 3000.00, 'AVAILABLE', '南向阳光房'),
('A102', 'A栋', 1, '双人间', 2, 2500.00, 'AVAILABLE', '标准双人间'),
('A201', 'A栋', 2, '单人间', 1, 3500.00, 'AVAILABLE', '豪华单人间'),
('B101', 'B栋', 1, '三人间', 3, 2000.00, 'AVAILABLE', '经济三人间'),
('B201', 'B栋', 2, '双人间', 2, 2800.00, 'MAINTENANCE', '正在翻新中');
