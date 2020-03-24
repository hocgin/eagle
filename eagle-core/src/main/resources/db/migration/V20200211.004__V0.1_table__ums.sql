DROP TABLE IF EXISTS `ums_account`;
CREATE TABLE `ums_account`
(
    `id`              BIGINT AUTO_INCREMENT
        COMMENT 'ID',
    `nickname`        VARCHAR(10)         NOT NULL
        COMMENT '昵称;显示使用',
    `username`        VARCHAR(20)         NOT NULL UNIQUE
        COMMENT '用户名;唯一,登录使用',
    `email`           VARCHAR(20) UNIQUE
        COMMENT '邮箱;唯一,登录使用',
    `phone`           VARCHAR(20) UNIQUE
        COMMENT '手机号码;唯一,登录使用',
    `password`        VARCHAR(100)        NOT NULL
        COMMENT '密码',
    `avatar`          VARCHAR(255)
        COMMENT '头像地址',
    `gender`          TINYINT(1) UNSIGNED NOT NULL
        COMMENT '性别(0:女, 1:男)',
    `expired`         TINYINT(1) UNSIGNED DEFAULT 1
        COMMENT '过期状态(0:为过期状态;1:为正常状态)',
    `locked`          TINYINT(1) UNSIGNED DEFAULT 1
        COMMENT '锁定状态(0:为锁定状态;1:为正常状态)',
    `enabled`         TINYINT(1) UNSIGNED DEFAULT 1
        COMMENT '启用状态(0:为禁用状态;1:为正常状态)',
    `created_ip`      VARCHAR(15)
        COMMENT '注册时使用的IP',
    --
    `created_at`      DATETIME(6)         NOT NULL
        COMMENT '创建时间',
    `creator`         BIGINT              NOT NULL
        COMMENT '创建者',
    `last_updated_at` DATETIME(6)
        COMMENT '更新时间',
    `last_updater`    BIGINT
        COMMENT '更新者',

    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 账号表';

DROP TABLE IF EXISTS `ums_employee`;
CREATE TABLE `ums_employee`
(
    `id` BIGINT AUTO_INCREMENT
        COMMENT 'ID, 同 ums_account ID',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 员工表';

DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member`
(
    `id` BIGINT AUTO_INCREMENT
        COMMENT 'ID, 同 ums_account ID',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 会员表';

/**
 * === 基础数据 ===
 */
INSERT INTO `ums_account`(`id`, `nickname`, `username`, `email`, `phone`, `password`, `avatar`, `gender`, `created_ip`,
                          `created_at`, `creator`)
    VALUE (1, 'admin', 'admin', 'admin@example.com', '13600747016', '{noop}hocgin',
           'https://avataaars.io/?avatarStyle=Circle&topType=WinterHat4&accessoriesType=Prescription02&hatColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=Blue02&eyeType=Wink&eyebrowType=UpDown&mouthType=Smile&skinColor=Pale',
           1,
           '127.0.0.1',
           NOW(), 1);

DROP TABLE IF EXISTS `ums_authority`;
CREATE TABLE `ums_authority`
(
    `id`              BIGINT AUTO_INCREMENT
        COMMENT 'ID',
    `title`           varchar(25)  NOT NULL
        COMMENT '权限名称',
    `type`            tinyint(2)   NOT NULL
        COMMENT '权限类型 [按钮, 菜单]',
    `authority_code`  varchar(64)  NOT NULL UNIQUE
        COMMENT '权限授权码',
    `platform`        INT(10)      NOT NULL
        COMMENT '平台编号',
    `parent_id`       BIGINT
        COMMENT '父级ID, 顶级为 NULL',
    `tree_path`       varchar(255) NOT NULL
        COMMENT '树路径，组成方式: /父路径/当前ID',
    `enabled`         TINYINT(1) UNSIGNED   DEFAULT 1
        COMMENT '启用状态[0:为禁用状态;1:为正常状态]',
    `sort`            INT(10)      NOT NULL DEFAULT 1000
        COMMENT '排序, 从大到小降序',
    --
    `created_at`      DATETIME(6)  NOT NULL
        COMMENT '创建时间',
    `creator`         BIGINT       NOT NULL
        COMMENT '创建者',
    `last_updated_at` DATETIME(6)
        COMMENT '更新时间',
    `last_updater`    BIGINT
        COMMENT '更新者',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 权限表';

DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role`
(
    `id`              BIGINT AUTO_INCREMENT
        COMMENT 'ID',
    `title`           varchar(25) NOT NULL
        COMMENT '角色名称',
    `role_code`       varchar(20) NOT NULL UNIQUE
        COMMENT '角色授权码',
    `platform`        INT(10)     NOT NULL
        COMMENT '平台编号',
    `remark`          varchar(255)
        COMMENT '角色描述',
    `enabled`         TINYINT(1) UNSIGNED DEFAULT 1
        COMMENT '启用状态[0:为禁用状态;1:为正常状态]',
    --
    `created_at`      DATETIME(6) NOT NULL
        COMMENT '创建时间',
    `creator`         BIGINT      NOT NULL
        COMMENT '创建者',
    `last_updated_at` DATETIME(6)
        COMMENT '更新时间',
    `last_updater`    BIGINT
        COMMENT '更新者',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 角色表';

DROP TABLE IF EXISTS `ums_role_authority`;
CREATE TABLE `ums_role_authority`
(
    `id`           BIGINT AUTO_INCREMENT
        COMMENT 'ID',
    `role_id`      BIGINT NOT NULL
        COMMENT 'ums_role ID',
    `authority_id` BIGINT NOT NULL
        COMMENT 'ums_authority ID',

    UNIQUE (`role_id`, `authority_id`),
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 角色-权限表';

DROP TABLE IF EXISTS `ums_role_account`;
CREATE TABLE `ums_role_account`
(
    `id`         BIGINT AUTO_INCREMENT
        COMMENT 'ID',
    `role_id`    BIGINT NOT NULL
        COMMENT 'ums_role ID',
    `account_id` BIGINT NOT NULL
        COMMENT 'ums_account ID',
    UNIQUE (`role_id`, `account_id`),
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 角色-账号表';

# 权限
# -- 主页
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (1, '主页', 1, 'index', 0, null, '/1', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (2, '控制台', 1, 'dashboard', 0, 1, '/1/2', NOW(), 1);

# -- 开发工具
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (3, '开发工具', 1, 'devtools', 0, null, '/3', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`, `sort`)
    VALUE (4, '数据字典', 1, 'data-dict', 0, 3, '/3/4', NOW(), 1, 0);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (5, '测试5', 1, 'test5', 0, 3, '/3/5', NOW(), 1);

# -- 访问控制
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (6, '访问控制', 1, 'access', 0, null, '/6', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (7, '权限管理', 1, 'authority', 0, 6, '/6/7', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (8, '角色管理', 1, 'role', 0, 6, '/6/8', NOW(), 1);

-- 用户中心
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (9, '用户中心', 1, 'ums', 0, null, '/9', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (10, '账号管理', 1, 'ums:account', 0, 9, '/9/10', NOW(), 1);

-- 商品系统
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (11, '商品系统', 1, 'pms', 0, null, '/11', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (12, '商品管理', 1, 'pms:product', 0, 11, '/11/12', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (13, '品类管理', 1, 'pms:product-category', 0, 11, '/11/13', NOW(), 1);

-- 订单系统
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (14, '订单系统', 1, 'oms', 0, null, '/14', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (15, '订单管理', 1, 'oms:order', 0, 14, '/14/15', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (16, '退费管理', 1, 'oms:order-refund-apply', 0, 14, '/14/16', NOW(), 1);


# 角色
INSERT INTO ums_role(`id`, `title`, `role_code`, `platform`, `created_at`, `creator`)
    VALUE (1, '超级管理员', 'SUPER_ADMIN', 0, NOW(), 1);
INSERT INTO ums_role(`id`, `title`, `role_code`, `platform`, `created_at`, `creator`)
    VALUE (2, 'EAGLE 后台', 'EAGLE', 0, NOW(), 1);
INSERT INTO ums_role(`id`, `title`, `role_code`, `platform`, `created_at`, `creator`)
    VALUE (3, 'EAGLE 客户端', 'MINI_EAGLE', 0, NOW(), 1);

# 赋予角色权限
INSERT INTO ums_role_authority(`role_id`, `authority_id`)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (1, 16);

# 赋予账号角色
INSERT INTO ums_role_account(`role_id`, `account_id`)
VALUES (1, 1);
INSERT INTO ums_role_account(`role_id`, `account_id`)
VALUES (2, 1);
INSERT INTO ums_role_account(`role_id`, `account_id`)
VALUES (3, 1);
