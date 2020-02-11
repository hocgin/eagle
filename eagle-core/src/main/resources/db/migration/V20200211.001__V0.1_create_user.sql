DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account`
(
    `id`              INT(10) UNSIGNED AUTO_INCREMENT
        COMMENT 'ID',
    `nick_name`       VARCHAR(10)         NOT NULL
        COMMENT '昵称;显示使用',
    `username`        VARCHAR(20)         NOT NULL UNIQUE
        COMMENT '用户名;唯一,登录使用',
    `email`           VARCHAR(20) UNIQUE
        COMMENT '邮箱;唯一,登录使用',
    `phone`           VARCHAR(20) UNIQUE
        COMMENT '手机号码;唯一,登录使用',
    `password`        VARCHAR(100)        NOT NULL
        COMMENT '密码',
    `avatar`          VARCHAR(129)
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
    `creator`         INT(10) UNSIGNED    NOT NULL
        COMMENT '创建者',
    `last_updated_at` DATETIME(6)
        COMMENT '更新时间',
    `last_updater`    INT(10) UNSIGNED
        COMMENT '更新者',

    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 账号表';

DROP TABLE IF EXISTS `t_staff`;
CREATE TABLE `t_staff`
(
    `id` INT(10) UNSIGNED
        COMMENT 'ID, 同 t_account ID',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 员工表';

DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member`
(
    `id` INT(10) UNSIGNED
        COMMENT 'ID, 同 t_account ID',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[用户模块] 会员表';