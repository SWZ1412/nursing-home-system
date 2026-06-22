# 🏥 养老院管理信息系统

基于 Spring Boot 的养老院智能管理系统，覆盖老人照护、医疗管理、收费运营等全流程。

## ✨ 功能模块

| 模块 | 说明 |
|------|------|
| 👴 **老人管理** | 老人入住/离院、信息维护、房间分配、快速统计 |
| 🏠 **房间管理** | 房间类型/容量/价格、入住数自动统计、状态自动更新 |
| 💊 **药物管理** | 药品库存、低库存预警、分类管理 |
| 📋 **用药计划** | 老人用药方案、剂量频次、执行状态跟踪 |
| ❤️ **健康记录** | 体温/血压/血糖/心率、护理备注 |
| 💰 **收费管理** | 账单生成、缴费、逾期提醒、删除撤销 |
| 🚶 **访客管理** | 访客登记、离开登记 |
| 🔒 **安全管理** | 事故记录、处理跟踪、严重程度分级 |
| 👤 **用户管理** | 管理员/医生/护士、角色权限、密码重置 |

## 🛠 技术栈

| 层级 | 技术 |
|------|------|
| 后端框架 | Spring Boot 2.x |
| 模板引擎 | Thymeleaf |
| 数据库访问 | MyBatis（注解方式） |
| 数据库 | MySQL 8.0 |
| 前端 | 原生 JavaScript + CSS3 |
| 分页 | PageHelper |
| 密码加密 | MD5 |

## 🚀 快速启动

### 1. 创建数据库

执行 `src/main/resources/db/init.sql` 脚本，会自动建库建表并插入初始数据。

### 2. 修改配置

编辑 `src/main/resources/application.properties`：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nursing_home?useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=你的密码
```

### 3. 启动应用

```bash
./mvnw spring-boot:run
```

### 4. 访问系统

打开浏览器访问 `http://localhost:8080`

**默认管理员账号**：`admin` / `admin123`

## 📁 项目结构

```
src/main/java/com/nursinghome/
├── controller/    # 控制器层
├── service/       # 服务接口
│   └── impl/      # 服务实现
├── mapper/        # MyBatis Mapper
├── entity/        # 实体类
├── common/        # 公共类（Result、异常处理）
├── config/        # 配置类
└── interceptor/   # 登录拦截器
src/main/resources/
├── templates/     # Thymeleaf 页面模板
├── static/css/    # 样式文件
├── db/            # 数据库初始化脚本
└── application.properties
```

## 🎨 界面预览

- 深色高级登录页（粒子动画 + 玻璃拟态）
- 绿色森林主题 UI
- 侧边栏分类导航 + 自动高亮
- 首页实时数据面板（在住老人、房间入住率、用药概览、低库存预警）
- 表格快速统计卡片
- 弹窗交互动画

## 📄 License

MIT
