DROP TABLE IF EXISTS `com_system_settings`;
CREATE TABLE `com_system_settings`
(
    `id`              BIGINT AUTO_INCREMENT
        COMMENT 'ID',
    --
    `title`           VARCHAR(32)  NOT NULL
        COMMENT '配置名称',
    `remark`          VARCHAR(255) NOT NULL
        COMMENT '配置备注',
    `config_code`     VARCHAR(32)  NOT NULL
        COMMENT '配置码',
    `value`           VARCHAR(1024)
        COMMENT '配置值',
    --
    `created_at`      DATETIME(6)  NOT NULL
        COMMENT '创建时间',
    `creator`         BIGINT       NOT NULL
        COMMENT '创建者',
    `last_updated_at` DATETIME(6)
        COMMENT '更新时间',
    `last_updater`    BIGINT
        COMMENT '更新者',

    UNIQUE KEY (`config_code`),
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[基础模块] 系统配置表';
--
DROP TABLE IF EXISTS `com_district`;
CREATE TABLE `com_district`
(
    id        BIGINT AUTO_INCREMENT,
    parent_id bigint,
    --
    tree_path varchar(255)        NOT NULL
        COMMENT '树路径，组成方式: /父路径/当前ID',
    enabled   TINYINT(1) UNSIGNED NOT NULL DEFAULT 1
        COMMENT '启用状态[0:为禁用状态;1:为正常状态]',
    ad_code   VARCHAR(32)         NOT NULL
        COMMENT '区域编码',
    city_code VARCHAR(32)
        COMMENT '城市编码',
    level     VARCHAR(32)         NOT NULL
        COMMENT '城市规划级别',
    lat       decimal(10, 6)
        COMMENT '中心(纬度)',
    lng       decimal(10, 6)
        COMMENT '中心(经度)',
    title     VARCHAR(32)         NOT NULL
        COMMENT '名称',
    --
    UNIQUE KEY (tree_path),
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[基础模块] 城市规划表';
--
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
    original_url VARCHAR(1024) NOT NULL
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
    nation            VARCHAR(32)
        COMMENT '国家',
    province          VARCHAR(32)
        COMMENT '省份',
    city              VARCHAR(32)
        COMMENT '城市',
    operator          VARCHAR(32)
        COMMENT '运营商',
    zip_code          VARCHAR(32)
        COMMENT '邮编',
    city_code         VARCHAR(32)
        COMMENT '城市编号',
    system_os         VARCHAR(32)
        COMMENT '系统',
    system_version    VARCHAR(32)
        COMMENT '系统版本',
    platform          VARCHAR(32)
        COMMENT '平台',
    engine            VARCHAR(32)
        COMMENT '内核',
    engine_version    VARCHAR(32)
        COMMENT '内核版本',
    supporter         VARCHAR(32)
        COMMENT '载体',
    supporter_version VARCHAR(32)
        COMMENT '载体版本',
    shell             VARCHAR(32)
        COMMENT '外壳',
    shell_version     VARCHAR(32)
        COMMENT '外壳版本',
    net_type          VARCHAR(32)
        COMMENT '网络类型',
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
    `title`           VARCHAR(64) NOT NULL
        COMMENT '字典名称',
    `code`            VARCHAR(64) NOT NULL UNIQUE
        COMMENT '字典标识',
    `remark`          VARCHAR(255)
        COMMENT '备注',
    `enabled`         TINYINT(2)  NOT NULL DEFAULT 1
        COMMENT '启用状态[0:为禁用状态;1:为正常状态]',
    --
    `created_at`      DATETIME(6) NOT NULL
        COMMENT '创建时间',
    `creator`         BIGINT
        COMMENT '创建者',
    `last_updated_at` DATETIME(6)
        COMMENT '更新时间',
    `last_updater`    BIGINT
        COMMENT '更新者',
    --
    UNIQUE (`code`),
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
    `title`           varchar(64) NOT NULL
        COMMENT '字典项名称',
    `code`            varchar(64) NOT NULL
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
    `creator`         BIGINT
        COMMENT '创建者',
    `last_updated_at` DATETIME(6)
        COMMENT '更新时间',
    `last_updater`    BIGINT
        COMMENT '更新者',
    --
    UNIQUE (`dict_id`, `code`),
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT = '[基础模块] 数据字典项表';

DROP TABLE IF EXISTS `com_persistence_message`;
CREATE TABLE `com_persistence_message`
(
    id          BIGINT AUTO_INCREMENT,
    group_sn    VARCHAR(64)       NOT NULL
        COMMENT '消息组编号',
    destination VARCHAR(64)       NOT NULL
        COMMENT '消息目的地',
    payload     TEXT
        COMMENT '消息体',
    headers     TEXT
        COMMENT '消息头',
    published   TINYINT DEFAULT 0 NOT NULL
        COMMENT '消息状态[0=>为准备状态;1=>已发布]',
    prepared_at DATETIME(6)       NOT NULL,
    created_at  DATETIME(6)       NOT NULL,
    updated_at  DATETIME(6)       NULL,
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[基础模块] 持久化消息表';

