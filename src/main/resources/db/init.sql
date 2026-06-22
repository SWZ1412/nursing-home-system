-- ============================================
-- 养老院管理信息系统 - 数据库初始化脚本
-- ============================================

CREATE DATABASE IF NOT EXISTS nursing_home DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE nursing_home;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码(MD5)',
    `real_name` VARCHAR(50) COMMENT '真实姓名',
    `role` VARCHAR(20) DEFAULT 'NURSE' COMMENT '角色: ADMIN/DOCTOR/NURSE',
    `phone` VARCHAR(20) COMMENT '电话',
    `email` VARCHAR(100) COMMENT '邮箱',
    `status` TINYINT DEFAULT 1 COMMENT '状态: 1=启用 0=禁用',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 房间表
CREATE TABLE IF NOT EXISTS `room` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `room_no` VARCHAR(20) NOT NULL UNIQUE COMMENT '房间号',
    `building` VARCHAR(50) COMMENT '楼栋',
    `floor` INT COMMENT '楼层',
    `room_type` VARCHAR(20) COMMENT '房型: 单人间/双人间/三人间',
    `capacity` INT DEFAULT 1 COMMENT '容纳人数',
    `occupied` INT DEFAULT 0 COMMENT '已住人数',
    `price` DECIMAL(10,2) COMMENT '月租',
    `status` VARCHAR(20) DEFAULT 'AVAILABLE' COMMENT '状态: AVAILABLE/FULL/MAINTENANCE',
    `description` VARCHAR(255) COMMENT '描述',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间表';

-- 老人表
CREATE TABLE IF NOT EXISTS `elder` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `elder_no` VARCHAR(30) NOT NULL UNIQUE COMMENT '老人编号',
    `name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `gender` VARCHAR(10) COMMENT '性别: MALE/FEMALE',
    `birthday` DATE COMMENT '出生日期',
    `id_card` VARCHAR(20) COMMENT '身份证号',
    `phone` VARCHAR(20) COMMENT '电话',
    `emergency_contact` VARCHAR(50) COMMENT '紧急联系人',
    `emergency_phone` VARCHAR(20) COMMENT '紧急联系电话',
    `address` VARCHAR(200) COMMENT '住址',
    `admission_date` DATE COMMENT '入住日期',
    `health_status` VARCHAR(50) COMMENT '健康状况',
    `room_id` INT COMMENT '房间ID',
    `bed_no` VARCHAR(10) COMMENT '床位号',
    `status` VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '状态: ACTIVE/LEFT',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`room_id`) REFERENCES `room`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老人表';

-- 健康记录表
CREATE TABLE IF NOT EXISTS `health_record` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `elder_id` INT NOT NULL COMMENT '老人ID',
    `record_date` DATE NOT NULL COMMENT '记录日期',
    `temperature` DECIMAL(4,1) COMMENT '体温',
    `blood_pressure_systolic` INT COMMENT '收缩压',
    `blood_pressure_diastolic` INT COMMENT '舒张压',
    `blood_sugar` DECIMAL(5,1) COMMENT '血糖',
    `heart_rate` INT COMMENT '心率',
    `medication` VARCHAR(500) COMMENT '用药情况',
    `nurse_note` VARCHAR(1000) COMMENT '护理备注',
    `nurse_id` INT COMMENT '护理人员ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`elder_id`) REFERENCES `elder`(`id`),
    FOREIGN KEY (`nurse_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康记录表';

-- 药品表
CREATE TABLE IF NOT EXISTS `medicine` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `medicine_no` VARCHAR(30) NOT NULL UNIQUE COMMENT '药品编号',
    `name` VARCHAR(100) NOT NULL COMMENT '药品名称',
    `category` VARCHAR(50) COMMENT '分类',
    `specification` VARCHAR(100) COMMENT '规格',
    `unit` VARCHAR(20) COMMENT '单位',
    `price` DECIMAL(10,2) COMMENT '单价',
    `stock` INT DEFAULT 0 COMMENT '库存',
    `min_stock` INT DEFAULT 10 COMMENT '最低库存',
    `manufacturer` VARCHAR(200) COMMENT '生产厂家',
    `expiry_date` DATE COMMENT '有效期',
    `description` VARCHAR(500) COMMENT '描述',
    `status` TINYINT DEFAULT 1 COMMENT '状态: 1=正常 0=下架',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='药品表';

-- 用药计划表
CREATE TABLE IF NOT EXISTS `medication_plan` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `elder_id` INT NOT NULL COMMENT '老人ID',
    `medicine_id` INT NOT NULL COMMENT '药品ID',
    `dosage` VARCHAR(50) COMMENT '剂量',
    `frequency` VARCHAR(100) COMMENT '频次',
    `start_date` DATE COMMENT '开始日期',
    `end_date` DATE COMMENT '结束日期',
    `remark` VARCHAR(500) COMMENT '备注',
    `status` VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '状态: ACTIVE/STOPPED/COMPLETED',
    `doctor_id` INT COMMENT '开具医生ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`elder_id`) REFERENCES `elder`(`id`),
    FOREIGN KEY (`medicine_id`) REFERENCES `medicine`(`id`),
    FOREIGN KEY (`doctor_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用药计划表';

-- 账单表
CREATE TABLE IF NOT EXISTS `bill` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `bill_no` VARCHAR(30) NOT NULL UNIQUE COMMENT '账单编号',
    `elder_id` INT NOT NULL COMMENT '老人ID',
    `bill_type` VARCHAR(50) COMMENT '费用类型',
    `amount` DECIMAL(10,2) DEFAULT 0 COMMENT '金额',
    `paid_amount` DECIMAL(10,2) DEFAULT 0 COMMENT '已付金额',
    `status` VARCHAR(20) DEFAULT 'UNPAID' COMMENT '状态: UNPAID/PAID/PARTIAL',
    `bill_month` VARCHAR(10) COMMENT '账单月份',
    `due_date` DATE COMMENT '截止日期',
    `remark` VARCHAR(500) COMMENT '备注',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`elder_id`) REFERENCES `elder`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账单表';

-- 缴费记录表
CREATE TABLE IF NOT EXISTS `payment` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `payment_no` VARCHAR(30) NOT NULL UNIQUE COMMENT '缴费编号',
    `bill_id` INT COMMENT '账单ID',
    `elder_id` INT NOT NULL COMMENT '老人ID',
    `amount` DECIMAL(10,2) DEFAULT 0 COMMENT '缴费金额',
    `payment_method` VARCHAR(20) COMMENT '支付方式',
    `payment_time` DATETIME COMMENT '缴费时间',
    `operator_id` INT COMMENT '操作员ID',
    `remark` VARCHAR(500) COMMENT '备注',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`bill_id`) REFERENCES `bill`(`id`),
    FOREIGN KEY (`elder_id`) REFERENCES `elder`(`id`),
    FOREIGN KEY (`operator_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='缴费记录表';

-- 访客记录表
CREATE TABLE IF NOT EXISTS `visitor_record` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `visitor_name` VARCHAR(50) NOT NULL COMMENT '访客姓名',
    `id_card` VARCHAR(20) COMMENT '身份证号',
    `phone` VARCHAR(20) COMMENT '电话',
    `elder_id` INT NOT NULL COMMENT '被访老人ID',
    `visit_time` DATETIME COMMENT '来访时间',
    `leave_time` DATETIME COMMENT '离开时间',
    `purpose` VARCHAR(200) COMMENT '来访目的',
    `remark` VARCHAR(500) COMMENT '备注',
    `operator_id` INT COMMENT '登记人ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`elder_id`) REFERENCES `elder`(`id`),
    FOREIGN KEY (`operator_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='访客记录表';

-- 事故记录表
CREATE TABLE IF NOT EXISTS `accident_record` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `accident_no` VARCHAR(30) NOT NULL UNIQUE COMMENT '事故编号',
    `elder_id` INT NOT NULL COMMENT '老人ID',
    `employee_id` INT COMMENT '涉事员工ID',
    `accident_type` VARCHAR(50) COMMENT '事故类型',
    `accident_time` DATETIME COMMENT '事故时间',
    `location` VARCHAR(100) COMMENT '事故地点',
    `description` VARCHAR(1000) COMMENT '事故描述',
    `severity` VARCHAR(20) COMMENT '严重程度: MINOR/MODERATE/SEVERE',
    `handling_result` VARCHAR(1000) COMMENT '处理结果',
    `reporter_id` INT COMMENT '报告人ID',
    `report_time` DATETIME COMMENT '报告时间',
    `status` VARCHAR(20) DEFAULT 'REPORTED' COMMENT '状态: REPORTED/HANDLING/RESOLVED',
    FOREIGN KEY (`elder_id`) REFERENCES `elder`(`id`),
    FOREIGN KEY (`employee_id`) REFERENCES `user`(`id`),
    FOREIGN KEY (`reporter_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='事故记录表';

-- ============================================
-- 初始数据
-- ============================================

-- 初始管理员 (密码: admin123 的MD5值)
INSERT INTO `user` (`username`, `password`, `real_name`, `role`, `phone`, `email`, `status`)
VALUES ('admin', MD5('admin123'), '系统管理员', 'ADMIN', '13800000000', 'admin@nursinghome.com', 1);

-- 示例房间数据
INSERT INTO `room` (`room_no`, `building`, `floor`, `room_type`, `capacity`, `price`, `status`, `description`) VALUES
('A101', 'A栋', 1, '单人间', 1, 3000.00, 'AVAILABLE', '南向阳光房'),
('A102', 'A栋', 1, '双人间', 2, 2500.00, 'AVAILABLE', '标准双人间'),
('A201', 'A栋', 2, '单人间', 1, 3500.00, 'AVAILABLE', '豪华单人间'),
('B101', 'B栋', 1, '三人间', 3, 2000.00, 'AVAILABLE', '经济三人间'),
('B201', 'B栋', 2, '双人间', 2, 2800.00, 'MAINTENANCE', '正在翻新中');
