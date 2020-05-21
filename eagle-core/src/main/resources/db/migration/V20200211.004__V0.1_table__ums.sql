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
--
DROP TABLE IF EXISTS `ums_account_social`;
CREATE TABLE ums_account_social
(

    `id`              BIGINT      NOT NULL
        COMMENT 'ID',
    `registration_id` VARCHAR(32) NOT NULL
        COMMENT '社交类型',
    `account_id`      BIGINT      NOT NULL
        COMMENT '账号ID',
    `uid`             BIGINT      NOT NULL
        COMMENT '微博 UID',
    `created_at`      DATETIME(6) NOT NULL
        COMMENT '创建时间',
    UNIQUE KEY (`uid`, `registration_id`),
    UNIQUE KEY (`account_id`, `registration_id`),
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 账号x社交绑定表';
--
DROP TABLE IF EXISTS `ums_account_group`;
CREATE TABLE `ums_account_group`
(
    `id`              BIGINT AUTO_INCREMENT
        COMMENT 'ID',
    --
    `title`           VARCHAR(16) NOT NULL
        COMMENT '组名',
    `remark`          VARCHAR(32) NOT NULL
        COMMENT '组描述',
    `group_type`      INT(4)      NOT NULL
        COMMENT '分组类型, 如: 通用',
    `member_source`   INT(4)      NOT NULL
        COMMENT '成员来源: 0->所有, 1->自定义组员列表',
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
    COMMENT = '[用户模块] 账号分组表';
--
DROP TABLE IF EXISTS `ums_account_group_member`;
CREATE TABLE `ums_account_group_member`
(
    `id`         BIGINT AUTO_INCREMENT
        COMMENT 'ID',
    --
    `group_id`   BIGINT      NOT NULL
        COMMENT '组ID',
    `account_id` BIGINT      NOT NULL
        COMMENT '成员ID',
    --
    `created_at` DATETIME(6) NOT NULL
        COMMENT '创建时间',
    `creator`    BIGINT      NOT NULL
        COMMENT '创建者',
    UNIQUE KEY (`group_id`, `account_id`),
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 账号分组成员表';
--
DROP TABLE IF EXISTS `ums_account_address`;
CREATE TABLE `ums_account_address`
(
    `id`             BIGINT AUTO_INCREMENT
        COMMENT 'ID',
    --
    `account_id`     BIGINT       NOT NULL
        COMMENT 'ID',
    `name`           VARCHAR(8)   NOT NULL
        COMMENT '收件人姓名',
    `phone`          VARCHAR(16)  NOT NULL
        COMMENT '收件人手机号',
    `post_code`      VARCHAR(100)
        COMMENT '邮政编码',
    `province`       VARCHAR(100) NOT NULL
        COMMENT '省份',
    `city`           VARCHAR(100) NOT NULL
        COMMENT '城市',
    `region`         VARCHAR(100) NOT NULL
        COMMENT '区',
    `detail_address` VARCHAR(128)
        COMMENT '详细地址(街道)',
    `is_default`     TINYINT(1)   NOT NULL
        DEFAULT 0,
    --
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 收货地址表';
--
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
    VALUE (1, 'admin', 'admin', 'hocgin@gmai.com', '13600747016', '{noop}hocgin',
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
    VALUE (4, '数据字典', 1, 'devtools:data-dict', 0, 3, '/3/4', NOW(), 1, 0);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (5, '请求日志', 1, 'devtools:request-log', 0, 3, '/3/5', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (22, '短链接', 1, 'devtools:short-url', 0, 3, '/3/22', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (23, '功能调试', 1, 'devtools:debug', 0, 3, '/3/23', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (24, '评论功能', 1, 'devtools:debug:comment', 0, 23, '/3/23/24', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (25, '系统配置', 1, 'devtools:settings', 0, 3, '/3/25', NOW(), 1);

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
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (26, '组管理', 1, 'ums:group', 0, 9, '/9/26', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (27, '组详情', 1, 'ums:group:detail', 0, 26, '/9/26/27', NOW(), 1);

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
    VALUE (16, '订单详情', 1, 'oms:order:detail', 0, 15, '/14/15/16', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (17, '退费管理', 1, 'oms:order-refund-apply', 0, 14, '/14/17', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (18, '退费申请详情', 1, 'oms:order-refund-apply:detail', 0, 17, '/14/17/18', NOW(), 1);

-- 营销系统
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (19, '营销系统', 1, 'mkt', 0, null, '/19', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (20, '优惠券模版', 1, 'mkt:coupon', 0, 19, '/19/20', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (21, '活动管理', 1, 'mkt:activity', 0, 19, '/19/21', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (28, '优惠券模版详情', 1, 'mkt:coupon:detail', 0, 20, '/19/20/28', NOW(), 1);

INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (29, '微信', 1, 'wx', 0, null, '/29', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (30, '公众号配置', 1, 'wx:mp-config', 0, 29, '/29/30', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (31, '微信用户', 1, 'wx:mp-user', 0, 29, '/29/31', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (32, '微信菜单', 1, 'wx:mp-menu', 0, 29, '/29/32', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (33, '微信素材', 1, 'wx:mp-material', 0, 29, '/29/33', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (34, '消息模版', 1, 'wx:mp-message-template', 0, 29, '/29/34', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (35, '回复规则', 1, 'wx:mp-reply-rule', 0, 29, '/29/35', NOW(), 1);
INSERT INTO ums_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                          `creator`)
    VALUE (36, '用户标签', 1, 'wx:mp-user-tags', 0, 29, '/29/36', NOW(), 1);


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
       (1, 16),
       (1, 17),
       (1, 18),
       (1, 19),
       (1, 20),
       (1, 21),
       (1, 22),
       (1, 23),
       (1, 24),
       (1, 25),
       (1, 26),
       (1, 27),
       (1, 28),
       (1, 29),
       (1, 30),
       (1, 31),
       (1, 32),
       (1, 33),
       (1, 34),
       (1, 35),
       (1, 36);

# 赋予账号角色
INSERT INTO ums_role_account(`role_id`, `account_id`)
VALUES (1, 1);
INSERT INTO ums_role_account(`role_id`, `account_id`)
VALUES (2, 1);
INSERT INTO ums_role_account(`role_id`, `account_id`)
VALUES (3, 1);
