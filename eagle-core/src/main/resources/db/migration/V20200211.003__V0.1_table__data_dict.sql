DROP TABLE IF EXISTS `t_data_dict`;
CREATE TABLE `t_data_dict`
(
    `id`              BIGINT AUTO_INCREMENT
        COMMENT 'ID',
    `title`           VARCHAR(25) NOT NULL
        COMMENT '字典名称',
    `code`            VARCHAR(25) NOT NULL UNIQUE
        COMMENT '字典标识',
    `remark`          VARCHAR(255)
        COMMENT '备注',
    `enabled`         TINYINT(2)  NOT NULL DEFAULT 1
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
    --
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[基础模块] 数据字典表';

DROP TABLE IF EXISTS `t_data_dict_item`;
CREATE TABLE `t_data_dict_item`
(
    `id`              BIGINT AUTO_INCREMENT
        COMMENT 'ID',
    `dict_id`         BIGINT      NOT NULL
        COMMENT 't_data_dict ID',
    `title`           varchar(25) NOT NULL
        COMMENT '字典项名称',
    `code`            varchar(25) NOT NULL
        COMMENT '字典标识',
    `remark`          varchar(255)
        COMMENT '备注',
    `sort`            INT(10)     NOT NULL DEFAULT 1000
        COMMENT '排序, 从大到小降序',
    `enabled`         tinyint(2)  NOT NULL DEFAULT 1
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

#  平台类型
INSERT INTO `t_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                          `created_at`, `creator`)
    VALUE (3, '平台类型', 'platform', '平台类型', 1,
           NOW(), 1);
INSERT INTO `t_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                               `created_at`, `creator`)
    VALUE (3, 'Eagle', '0', '平台类型:Eagle', 1,
           NOW(), 1);

#  权限类型
INSERT INTO `t_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                          `created_at`, `creator`)
    VALUE (4, '权限类型', 'authorityType', '权限类型', 1,
           NOW(), 1);
INSERT INTO `t_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                               `created_at`, `creator`)
    VALUE (4, '按钮', '0', '权限类型:按钮', 1,
           NOW(), 1);
INSERT INTO `t_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                               `created_at`, `creator`)
    VALUE (4, '菜单', '1', '权限类型:菜单', 1,
           NOW(), 1);

#  通知类型
INSERT INTO `t_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                          `created_at`, `creator`)
    VALUE (5, '通知类型', 'notifyType', '通知类型', 1,
           NOW(), 1);
INSERT INTO `t_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                               `created_at`, `creator`)
    VALUE (5, '私信', '0', '通知类型:私信', 1,
           NOW(), 1);
INSERT INTO `t_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                               `created_at`, `creator`)
    VALUE (5, '公告', '1', '通知类型:公告', 1,
           NOW(), 1);
INSERT INTO `t_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                               `created_at`, `creator`)
    VALUE (5, '订阅', '2', '通知类型:订阅', 1,
           NOW(), 1);

#  订阅类型
INSERT INTO `t_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                          `created_at`, `creator`)
    VALUE (6, '订阅类型', 'subjectType', '订阅类型', 1,
           NOW(), 1);
INSERT INTO `t_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                               `created_at`, `creator`)
    VALUE (6, '评论', '0', '订阅类型:评论', 1,
           NOW(), 1);
