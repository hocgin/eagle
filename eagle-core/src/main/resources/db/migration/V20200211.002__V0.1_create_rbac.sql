DROP TABLE IF EXISTS `t_authority`;
CREATE TABLE `t_authority`
(
    `id`              INT(10) UNSIGNED AUTO_INCREMENT
        COMMENT 'ID',
    `title`           varchar(25)      NOT NULL
        COMMENT '权限名称',
    `type`            tinyint(2)       NOT NULL
        COMMENT '权限类型 [按钮, 菜单]',
    `authority_code`  varchar(20)      NOT NULL UNIQUE
        COMMENT '权限授权码',
    `method`          varchar(10)      NOT NULL
        COMMENT '请求方式 [*, GET, POST..]',
    `uri`             varchar(255)     NOT NULL
        COMMENT '请求URL',
    `parent_id`        INT(10)
        COMMENT '父级ID, 顶级为 NULL',
    `tree_path`       varchar(255)     NOT NULL
        COMMENT '树路径，组成方式: /父路径/当前ID',
    `enabled`         TINYINT(1) UNSIGNED       DEFAULT 1
        COMMENT '启用状态[0:为禁用状态;1:为正常状态]',
    `sort`            INT(10)          NOT NULL DEFAULT 1000
        COMMENT '排序, 从大到小降序',
    --
    `created_at`      DATETIME(6)      NOT NULL
        COMMENT '创建时间',
    `creator`         INT(10) UNSIGNED NOT NULL
        COMMENT '创建者',
    `last_updated_at` DATETIME(6)
        COMMENT '更新时间',
    `last_updater`    INT(10) UNSIGNED
        COMMENT '更新者',
    UNIQUE (`method`, `uri`),
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 权限表';

DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`
(
    `id`              INT(10) UNSIGNED AUTO_INCREMENT
        COMMENT 'ID',
    `title`           varchar(25)      NOT NULL
        COMMENT '角色名称',
    `role_code`       varchar(20)      NOT NULL UNIQUE
        COMMENT '角色授权码',
    `description`     varchar(255)
        COMMENT '角色描述',
    `enabled`         TINYINT(1) UNSIGNED DEFAULT 1
        COMMENT '启用状态[0:为禁用状态;1:为正常状态]',
    --
    `created_at`      DATETIME(6)      NOT NULL
        COMMENT '创建时间',
    `creator`         INT(10) UNSIGNED NOT NULL
        COMMENT '创建者',
    `last_updated_at` DATETIME(6)
        COMMENT '更新时间',
    `last_updater`    INT(10) UNSIGNED
        COMMENT '更新者',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 角色表';

DROP TABLE IF EXISTS `t_role_authority`;
CREATE TABLE `t_role_authority`
(
    `id`           INT(10) UNSIGNED AUTO_INCREMENT
        COMMENT 'ID',
    `role_id`      INT(10) UNSIGNED NOT NULL
        COMMENT 't_role ID',
    `authority_id` INT(10) UNSIGNED NOT NULL
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
    `id`         INT(10) UNSIGNED AUTO_INCREMENT
        COMMENT 'ID',
    `role_id`    INT(10) UNSIGNED NOT NULL
        COMMENT 't_role ID',
    `account_id` INT(10) UNSIGNED NOT NULL
        COMMENT 't_account ID',
    UNIQUE (`role_id`, `account_id`),
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 角色-账号表';

DROP TABLE IF EXISTS `t_authority_account`;
CREATE TABLE `t_authority_account`
(
    `id`           INT(10) UNSIGNED AUTO_INCREMENT
        COMMENT 'ID',
    `authority_id` INT(10) UNSIGNED NOT NULL
        COMMENT 't_authority ID',
    `account_id`   INT(10) UNSIGNED NOT NULL
        COMMENT 't_account ID',
    UNIQUE (`authority_id`, `account_id`),
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 权限-账号表';
