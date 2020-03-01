DROP TABLE IF EXISTS `t_authority`;
CREATE TABLE `t_authority`
(
    `id`              BIGINT AUTO_INCREMENT
        COMMENT 'ID',
    `title`           varchar(25)  NOT NULL
        COMMENT '权限名称',
    `type`            tinyint(2)   NOT NULL
        COMMENT '权限类型 [按钮, 菜单]',
    `authority_code`  varchar(20)  NOT NULL UNIQUE
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

DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`
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

DROP TABLE IF EXISTS `t_role_authority`;
CREATE TABLE `t_role_authority`
(
    `id`           BIGINT AUTO_INCREMENT
        COMMENT 'ID',
    `role_id`      BIGINT NOT NULL
        COMMENT 't_role ID',
    `authority_id` BIGINT NOT NULL
        COMMENT 't_authority ID',

    UNIQUE (`role_id`, `authority_id`),
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 角色-权限表';

DROP TABLE IF EXISTS `t_role_account`;
CREATE TABLE `t_role_account`
(
    `id`         BIGINT AUTO_INCREMENT
        COMMENT 'ID',
    `role_id`    BIGINT NOT NULL
        COMMENT 't_role ID',
    `account_id` BIGINT NOT NULL
        COMMENT 't_account ID',
    UNIQUE (`role_id`, `account_id`),
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 角色-账号表';

# 权限
# -- 主页
INSERT INTO t_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                        `creator`)
    VALUE (1, '主页', 1, 'index', 0, null, '/1', NOW(), 1);
INSERT INTO t_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                        `creator`)
    VALUE (2, '控制台', 1, 'dashboard', 0, 1, '/1/2', NOW(), 1);
# -- 开发工具
INSERT INTO t_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                        `creator`)
    VALUE (3, '开发工具', 1, 'devtools', 0, null, '/3', NOW(), 1);
INSERT INTO t_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                        `creator`)
    VALUE (4, '测试', 1, 'test', 0, 3, '/3/4', NOW(), 1);
INSERT INTO t_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                        `creator`)
    VALUE (5, '测试5', 1, 'test5', 0, 4, '/3/4/5', NOW(), 1);
# -- 访问控制
INSERT INTO t_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                        `creator`)
    VALUE (6, '访问控制', 1, 'access', 0, null, '/6', NOW(), 1);
INSERT INTO t_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                        `creator`)
    VALUE (7, '权限管理', 1, 'authority', 0, 6, '/6/7', NOW(), 1);
INSERT INTO t_authority(`id`, `title`, `type`, `authority_code`, `platform`, `parent_id`, `tree_path`, `created_at`,
                        `creator`)
    VALUE (8, '角色管理', 1, 'role', 0, 6, '/6/8', NOW(), 1);

# 角色
INSERT INTO t_role(`id`, `title`, `role_code`, `platform`, `created_at`, `creator`)
    VALUE (1, '超级管理员', 'SUPER_ADMIN', 0, NOW(), 1);

# 赋予角色权限
INSERT INTO t_role_authority(`id`, `role_id`, `authority_id`)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 1, 3),
       (4, 1, 4),
       (5, 1, 5),
       (6, 1, 6),
       (7, 1, 7),
       (8, 1, 8);

# 赋予账号角色
INSERT INTO t_role_account(`id`, `role_id`, `account_id`)
VALUES (1, 1, 1);
