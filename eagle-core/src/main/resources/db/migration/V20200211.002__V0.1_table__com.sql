DROP TABLE IF EXISTS `com_change_log`;
CREATE TABLE `com_change_log`
(
    id          BIGINT AUTO_INCREMENT,
    log_sn      VARCHAR(32)  NOT NULL UNIQUE,
    ref_type    INT(3)       NOT NULL
        COMMENT '业务类型: 如: 订单',
    ref_id      BIGINT       NOT NULL
        COMMENT '业务ID: 如: 订单ID',
    change_type INT(3)       NOT NULL
        COMMENT '操作类型: 0->新增, 1->修改, 2->删除',
    created_at  TIMESTAMP(6) NOT NULL
        COMMENT '创建时间',
    creator     BIGINT
        COMMENT '创建人',
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[基础模块] 业务操作日志表';

DROP TABLE IF EXISTS `com_field_change`;
CREATE TABLE `com_field_change`
(
    id            BIGINT AUTO_INCREMENT,
    change_log_id BIGINT       NOT NULL
        COMMENT '业务操作日志',
    field_name    VARCHAR(32)  NOT NULL
        COMMENT '字段名',
    field_remark  VARCHAR(255) NOT NULL
        COMMENT '字段备注',
    change_remark VARCHAR(255) NOT NULL
        COMMENT '变更描述',
    before_value  VARCHAR(255) NOT NULL
        COMMENT '变更前',
    after_value   VARCHAR(255) NOT NULL
        COMMENT '变更后',

    FOREIGN KEY (`change_log_id`) REFERENCES com_change_log (`id`),
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[基础模块] 业务日志-字段变更记录表';


DROP TABLE IF EXISTS `com_short_url`;
CREATE TABLE `com_short_url`
(
    id           BIGINT AUTO_INCREMENT,
    code         VARCHAR(8)    NOT NULL UNIQUE
        COMMENT '短链码',
    original_url VARCHAR(1024) NOT NULL UNIQUE
        COMMENT '原链',
    `enabled`    TINYINT(2)    NOT NULL DEFAULT 1
        COMMENT '启用状态[0:为禁用状态;1:为正常状态]',
    created_at   TIMESTAMP(6)  NOT NULL
        COMMENT '创建时间',
    creator      BIGINT
        COMMENT '创建人',

    PRIMARY KEY (id)
)
    AUTO_INCREMENT = 10000
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[基础模块] 短链接表';

DROP TABLE IF EXISTS `com_request_log`;
CREATE TABLE `com_request_log`
(
    id                BIGINT AUTO_INCREMENT,
    method            VARCHAR(8)   NOT NULL
        COMMENT '请求方式',
    uri               VARCHAR(512) NOT NULL
        COMMENT '请求URI',
    args              TEXT         NOT NULL
        COMMENT '请求体',
    ret               TEXT
        COMMENT '响应体',
    exception         TEXT
        COMMENT '异常信息',
    logs              TEXT
        COMMENT '线程内日志',
    total_time_millis INT(5)       NOT NULL DEFAULT 0
        COMMENT '请求耗时(ms)',
    mapping           VARCHAR(200) NOT NULL
        COMMENT '代码位置',
    source            VARCHAR(200)
        COMMENT '请求来源,URL上的参数source来标记',
    host              VARCHAR(200) NOT NULL
        COMMENT '请求头:host',
    user_agent        VARCHAR(200) NOT NULL
        COMMENT '请求头:user-agent',
    client_ip         VARCHAR(32)
        COMMENT '请求IP',
    enter_remark      VARCHAR(200)
        COMMENT '入口描述',
    created_at        TIMESTAMP(6) NOT NULL
        COMMENT '创建时间',
    creator           BIGINT
        COMMENT '创建人',

    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[基础模块] 请求日志表';

DROP TABLE IF EXISTS `com_file`;
CREATE TABLE `com_file`
(
    id         BIGINT AUTO_INCREMENT,
    filename   VARCHAR(200) NOT NULL DEFAULT 'Unknown'
        COMMENT '文件名',
    file_url   VARCHAR(200) NOT NULL
        COMMENT '链接地址',
    rel_id     BIGINT       NOT NULL
        COMMENT '业务ID',
    rel_type   INT(10)      NOT NULL
        COMMENT '业务类型',
    sort       INT(10)      NOT NULL DEFAULT 1000
        COMMENT '排序,默认:1000',
    created_at TIMESTAMP(6) NOT NULL
        COMMENT '创建时间',
    creator    BIGINT       NOT NULL
        COMMENT '创建人',

    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[基础模块] 文件引用表';

DROP TABLE IF EXISTS `com_data_dict`;
CREATE TABLE `com_data_dict`
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

DROP TABLE IF EXISTS `com_data_dict_item`;
CREATE TABLE `com_data_dict_item`
(
    `id`              BIGINT AUTO_INCREMENT
        COMMENT 'ID',
    `dict_id`         BIGINT      NOT NULL
        COMMENT 'com_data_dict ID',
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
    FOREIGN KEY (`dict_id`) REFERENCES com_data_dict (`id`),
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[基础模块] 数据字典项表';

/**
 * === 基础数据 ===
 */
#  启用状态
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (1, '启用状态', 'enabled', '启用状态', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (1, '开启', '1', '启用状态:开启', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (1, '禁用', '0', '启用状态:禁用', 1,
           NOW(), 1);

#  性别
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (2, '性别', 'gender', '性别', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (2, '男', '1', '性别:男', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (2, '女', '0', '性别:女', 1,
           NOW(), 1);

#  平台类型
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (3, '平台类型', 'platform', '平台类型', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (3, 'Eagle', '0', '平台类型:Eagle', 1,
           NOW(), 1);

#  权限类型
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (4, '权限类型', 'authorityType', '权限类型', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (4, '按钮', '0', '权限类型:按钮', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (4, '菜单', '1', '权限类型:菜单', 1,
           NOW(), 1);

#  通知类型
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (5, '通知类型', 'notifyType', '通知类型', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (5, '私信', '0', '通知类型:私信', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (5, '公告', '1', '通知类型:公告', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (5, '订阅', '2', '通知类型:订阅', 1,
           NOW(), 1);

#  订阅类型
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (6, '订阅类型', 'subjectType', '订阅类型', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (6, '评论', '0', '订阅类型:评论', 1,
           NOW(), 1);

#  过期状态
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (7, '过期状态', 'expired', '过期状态', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (7, '已过期', '0', '过期状态:已过期', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (7, '正常', '1', '过期状态:正常', 1,
           NOW(), 1);

#  锁定状态
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (8, '锁定状态', 'locked', '过期状态', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (8, '已锁定', '0', '过期状态:已锁定', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (8, '正常', '1', '过期状态:正常', 1,
           NOW(), 1);

#  删除状态
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (9, '删除状态', 'deleteStatus', '删除状态', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (9, '未删除', '0', '删除状态:未删除', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (9, '已删除', '1', '删除状态:已删除', 1,
           NOW(), 1);

#  上架状态
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (10, '上架状态', 'publishStatus', '上架状态', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (10, '下架', '0', '上架状态:下架', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (10, '上架', '1', '上架状态:上架', 1,
           NOW(), 1);

#  支付方式
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (11, '支付类型', 'orderPayType', '支付类型', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (11, '支付宝', '0', '支付类型:支付宝', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (11, '微信', '1', '支付类型:微信', 1,
           NOW(), 1);

#  订单来源
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (12, '订单来源', 'orderSourceType', '订单来源', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (12, 'Unknown', '0', '订单来源:未知', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (12, 'APP', '1', '订单来源:APP', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (12, 'PC', '2', '订单来源:PC', 1,
           NOW(), 1);


#  订单状态
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (13, '订单状态', 'orderStatus', '订单状态', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (13, '待付款', '0', '订单状态:待付款', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (13, '待发货', '1', '订单状态:待发货', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (13, '已发货', '2', '订单状态:已发货', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (13, '已完成', '3', '订单状态:已完成', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (13, '已关闭', '4', '订单状态:已关闭', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (13, '无效订单', '5', '订单状态:无效订单', 1,
           NOW(), 1);

#  支付方式
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (14, '确认状态', 'confirmStatus', '确认状态', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (14, '未确认', '0', '确认状态:未确认', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (14, '已确认', '1', '确认状态:已确认', 1,
           NOW(), 1);

#  退款申请状态
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (15, '退款申请状态', 'refundApplyStatus', '退款申请状态', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (15, '待处理', '0', '退款申请状态:待处理', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (15, '退货中', '1', '退款申请状态:退货中', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (15, '已完成', '2', '退款申请状态:已完成', 1,
           NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (15, '已拒绝', '3', '退款申请状态:已拒绝', 1,
           NOW(), 1);


#  支付方式
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (16, '折扣方式', 'couponType', '折扣方式', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (16, '满减', '0', '折扣方式:满减', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (16, '折扣', '1', '折扣方式:折扣', 1, NOW(), 1);

#  可用平台
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (17, '可用平台', 'couponPlatformType', '可用平台', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (17, '全部', '0', '可用平台:全部', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (17, '移动', '1', '可用平台:移动', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (17, 'PC', '2', '可用平台:PC', 1, NOW(), 1);

#  可用类型
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (18, '可用类型', 'couponUseType', '可用类型', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (18, '全场通用', '0', '可用类型:全场通用', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (18, '指定品类', '1', '可用类型:指定品类', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (18, '指定商品', '2', '可用类型:指定商品', 1, NOW(), 1);

#  可用类型
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (19, '优惠券使用状态', 'couponUseStatus', '优惠券使用状态', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (19, '未使用', '0', '优惠券使用状态:未使用', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (19, '已使用', '1', '优惠券使用状态:已使用', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (19, '已过期', '2', '优惠券使用状态:已过期', 1, NOW(), 1);

## 业务日志 - 变更类型
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (20, '业务日志 - 变更类型', 'changeLogChangeType', '业务日志 - 变更类型', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (20, '新增', '0', '业务日志 - 变更类型:新增', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (20, '更新', '1', '业务日志 - 变更类型:更新', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (20, '删除', '2', '业务日志 - 变更类型:删除', 1, NOW(), 1);

## 业务日志 - 业务类型
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (21, '业务日志 - 业务类型', 'changeLogRefType', '业务日志 - 业务类型', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (21, '订单日志', '0', '业务日志 - 业务类型:订单日志', 1, NOW(), 1);

## 购物车 - 购物车商品状态
INSERT INTO `com_data_dict`(`id`, `title`, `code`, `remark`, `enabled`,
                            `created_at`, `creator`)
    VALUE (21, '购物车 - 购物车商品状态', 'cartItemStatus', '购物车 - 购物车商品状态', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (21, '正常', '0', '购物车 - 购物车商品状态:正常', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (21, '已过期', '1', '购物车 - 购物车商品状态:已过期', 1, NOW(), 1);
INSERT INTO `com_data_dict_item`(`dict_id`, `title`, `code`, `remark`, `enabled`,
                                 `created_at`, `creator`)
    VALUE (21, '库存不足', '2', '购物车 - 购物车商品状态:库存不足', 1, NOW(), 1);


