DROP TABLE IF EXISTS `t_data_dict`;
CREATE TABLE `t_data_dict`
(
    `id`              INT(10) UNSIGNED AUTO_INCREMENT
        COMMENT 'ID',
    `title`           varchar(25)      NOT NULL
        COMMENT '字典名称',
    `code`            varchar(25)      NOT NULL UNIQUE
        COMMENT '字典标识',
    `remark`          varchar(255)
        COMMENT '备注',
    `enabled`         tinyint(2)       NOT NULL DEFAULT 1
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
    --
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[基础模块] 数据字典表';

DROP TABLE IF EXISTS `t_data_dict_item`;
CREATE TABLE `t_data_dict_item`
(
    `id`              INT(10) UNSIGNED AUTO_INCREMENT
        COMMENT 'ID',
    `dict_id`         INT(10) UNSIGNED NOT NULL
        COMMENT 't_data_dict ID',
    `title`           varchar(25)      NOT NULL
        COMMENT '字典项名称',
    `code`            varchar(25)      NOT NULL
        COMMENT '字典标识',
    `remark`          varchar(255)
        COMMENT '备注',
    `sort`            INT(10)          NOT NULL DEFAULT 1000
        COMMENT '排序, 从大到小降序',
    `enabled`         tinyint(2)       NOT NULL DEFAULT 1
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
    --
    UNIQUE (`dict_id`, `code`),
    FOREIGN KEY (`dict_id`) REFERENCES t_data_dict (`id`),
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[基础模块] 数据字典项表';

/**
 * === 基础数据 ===
 */
#  启用状态
INSERT INTO `t_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                          `created_at`, `creator`)
    VALUE (1, '启用状态', 'enabled', '启用状态', 1,
           NOW(), 1);
INSERT INTO `t_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                               `created_at`, `creator`)
    VALUE (1, '开启', '1', '启用状态:开启', 1,
           NOW(), 1);
INSERT INTO `t_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                               `created_at`, `creator`)
    VALUE (1, '禁用', '0', '启用状态:禁用', 1,
           NOW(), 1);


#  性别
INSERT INTO `t_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                          `created_at`, `creator`)
    VALUE (2, '性别', 'gender', '性别', 1,
           NOW(), 1);
INSERT INTO `t_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                               `created_at`, `creator`)
    VALUE (2, '男', '1', '性别:男', 1,
           NOW(), 1);
INSERT INTO `t_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                               `created_at`, `creator`)
    VALUE (2, '女', '0', '性别:女', 1,
           NOW(), 1);