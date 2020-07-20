DROP TABLE IF EXISTS `bmw_payment_app`;
CREATE TABLE `bmw_payment_app`
(
    id         bigint auto_increment,
    app_sn     bigint                     not null
        comment '接入方编号',
    title      VARCHAR(255)               NOT NULL
        COMMENT '标题',
    enabled    tinyint unsigned default 0 not null
        COMMENT '启用状态[0:为禁用状态;1:为正常状态]',
    --
    created_at datetime(6)                not null
        comment '创建时间',
    created_ip varchar(32)
        comment '创建ip',
    UNIQUE KEY (app_sn),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 接入方表';

DROP TABLE IF EXISTS `bmw_payment_platform`;
CREATE TABLE `bmw_payment_platform`
(
    id             bigint auto_increment,
    platform_appid varchar(255)               not null
        comment '支付平台的唯一标识',
    title          VARCHAR(255)               NOT NULL
        COMMENT '标题',
    platform_type  tinyint unsigned           not null
        comment '支付平台类型: 0=>AliPay; 1=>WxPay',
    enabled        tinyint unsigned default 0 not null
        COMMENT '启用状态[0:为禁用状态;1:为正常状态]',
    --
    created_at     datetime(6)                not null
        comment '创建时间',
    created_ip     varchar(32)
        comment '创建ip',
    UNIQUE KEY (platform_type, platform_appid),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 支付平台表';

DROP TABLE IF EXISTS `bmw_request_platform_log`;
CREATE TABLE `bmw_request_platform_log`
(
    id                  bigint auto_increment,
    app_id              bigint           not null
        comment '接入方应用',
    payment_platform_id bigint           not null
        comment '支付平台ID',
    out_request_sn      varchar(64)      not null
        comment '应用单号(接入方): [退款单号/交易单号]',
    request_sn          varchar(64)      not null
        comment '应用单号(网关): [退款单号/交易单号]',
    trade_sn            varchar(64)      not null
        comment '交易单号(网关)',
    request_header      text             not null
        comment '请求头',
    request_params      text             not null
        comment '请求参数',
    log_type            tinyint unsigned not null
        comment '日志类型: 0=>支付; 1=>退款; 2=>异步通知; 3=>同步通知; 4=>查询',
    --
    created_at          datetime(6)      not null
        comment '创建时间',
    created_ip          varchar(32)
        comment '创建ip',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 所有和第三方支付交易日志表';

DROP TABLE IF EXISTS `bmw_payment_trade`;
CREATE TABLE `bmw_payment_trade`
(
    id                  bigint auto_increment,
    app_id              bigint                     not null
        comment '接入方应用',
    out_trade_sn        varchar(64)                not null
        comment '交易单号(接入方)',
    --
    trade_sn            varchar(64)                not null
        comment '交易单号(网关)',
    total_fee           decimal(10, 2) default '0' not null
        comment '交易总金额',
    trade_status        tinyint        default 0   not null
        comment '交易状态: 0=>等待支付; 1=>待付款; 2=>完成支付; 3=>交易已关闭; 4=>支付失败',
    notify_url          varchar(255)
        comment '通知接入应用的地址',
    --
    buyer_pay_fee       decimal(10, 2)
        comment '最终买家实际支付金额(第三方回调时填充)',
    payment_platform_id bigint
        comment '最终支付平台ID(第三方回调时填充)',
    payment_way         tinyint unsigned
        comment '最终支付方式(第三方回调时填充)',
    trade_no            varchar(64)
        comment '最终第三方的交易单号(第三方回调填充)',
    wx_openid           varchar(255)
        comment '最终微信用户(仅微信支付)(第三方回调填充)',
    payment_at          datetime(6)
        comment '最终第三方支付成功的时间(第三方回调填充)',
    notify_at           datetime(6)
        comment '接收到第三方支付通知的时间',
    --
    created_at          datetime(6)                not null
        comment '创建时间',
    created_ip          varchar(32)
        comment '创建的IP',
    updated_at          datetime(6)
        comment '更新时间',
    updated_ip          varchar(32)
        comment '更新的IP',
    finish_at           datetime(6)
        comment '通知接入应用并完成交易的时间',
    expire_at           datetime(6)
        comment '订单过期时间',
    UNIQUE KEY (`app_id`, `out_trade_sn`),
    UNIQUE KEY (trade_sn),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 交易流水表';

DROP TABLE IF EXISTS `bmw_payment_record`;
CREATE TABLE `bmw_payment_record`
(
    id                  bigint auto_increment,
    trade_id            bigint           not null
        comment '交易单ID',
    payment_platform_id bigint           not null
        comment '支付平台ID',
    payment_way         tinyint unsigned not null
        comment '支付方式',
    wx_openid           varchar(255)
        comment '微信用户(仅微信支付)',
    --
    created_at          datetime(6)      not null
        comment '创建时间',
    created_ip          varchar(32)
        comment '创建的IP',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 支付记录表';

DROP TABLE IF EXISTS `bmw_refund_record`;
CREATE TABLE `bmw_refund_record`
(
    id                    bigint auto_increment,
    app_id                bigint                       not null
        comment '接入方应用',
    out_refund_sn         varchar(64)                  not null
        comment '退款单号(接入方)',
    --
    trade_sn              varchar(64)                  not null
        comment '交易单号(网关)',
    refund_sn             varchar(64)                  not null
        comment '退款单号(网关)',
    refund_trade_no       varchar(64)                  not null
        comment '退款单号(第三方)',
    refund_fee            decimal(10, 2)   default '0' not null
        comment '申请退款金额',
    refund_reason         varchar(255)
        comment '退款理由',
    refund_status         tinyint unsigned default '0' not null
        comment '退款状态: 0=>未退款; 1=>退款处理中; 2=>退款成功; 3=>退款失败',
    notify_url            varchar(255)
        comment '通知接入应用的地址',
    --
    settlement_refund_fee decimal(10, 2)   default '0' not null
        comment '实际退款金额',
    refund_at             datetime(6)
        comment '最终第三方退款成功的时间(第三方回调填充)',
    notify_at             datetime(6)
        comment '接收到第三方支付通知的时间',
    --
    created_at            datetime(6)                  not null
        comment '创建时间',
    created_ip            varchar(32)
        comment '创建ip',
    updated_at            datetime(6)
        comment '更新时间',
    update_ip             varchar(32)
        comment '更新ip',
    UNIQUE KEY (`app_id`, `out_refund_sn`),
    UNIQUE KEY (`refund_sn`),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 退款记录表';

DROP TABLE IF EXISTS `bmw_notify_app`;
CREATE TABLE `bmw_notify_app`
(
    id            bigint auto_increment,
    request_sn    varchar(64)                      not null
        comment '应用单号(网关): [退款单号/交易单号]',
    trade_sn      varchar(64)                      not null
        comment '交易单号(网关)',
    sign_type     varchar(10)      default 'RSA'   not null
        comment '采用的签名方式: MD5 RSA RSA2 HASH-MAC等',
    notify_type   tinyint unsigned default 0       not null
        comment '通知事件类型，0=>支付事件; 1=>退款事件',
    notify_status tinyint unsigned default 0       not null
        comment '通知事件状态: 0=>初始化; 1=>进行中; 2=>成功; 3=>失败; 4=>关闭',
    version       tinyint unsigned default 1       not null
        comment '版本',
    input_charset varchar(8)       default 'UTF-8' not null
        comment '字符编码',
    --
    finish_at     datetime(6)
        comment '完成通知时间',
    created_at    datetime(6)                      not null,
    updated_at    datetime(6),
    UNIQUE KEY (`request_sn`),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 事件通知列表';

DROP TABLE IF EXISTS `bmw_notify_app_log`;
CREATE TABLE `bmw_notify_app_log`
(
    id            bigint auto_increment,
    notify_app_id bigint                     not null
        comment '通知编号ID',
    notify_result tinyint unsigned default 0 not null
        comment '通知调用结果: 0=>初始化; 1=>响应成功; 2=>响应失败; 3=>超时失败',
    notify_body   JSON                       not null
        comment '通知内容',
    --
    created_at    datetime(6)                not null,
    updated_at    datetime(6),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 所有通知应用方日志表';

DROP TABLE IF EXISTS `bmw_payment_way_rule`;
CREATE TABLE `bmw_payment_way_rule`
(
    id         BIGINT AUTO_INCREMENT,
    app_id     BIGINT            NOT NULL
        COMMENT '接入方应用',
    title      VARCHAR(64)       NOT NULL
        COMMENT '规则名称',
    scene_code VARCHAR(32)       NOT NULL
        COMMENT '场景码',
    enabled    TINYINT DEFAULT 1 NOT NULL
        COMMENT '启用状态[0:为禁用状态;1:为正常状态]',
    created_at DATETIME(6)       NOT NULL,
    updated_at DATETIME(6)       NULL,
    UNIQUE KEY (scene_code),
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[支付网关] 支付渠道规则表';

DROP TABLE IF EXISTS `bmw_payment_way_rule_item`;
CREATE TABLE `bmw_payment_way_rule_item`
(
    id               BIGINT AUTO_INCREMENT,
    rule_id          BIGINT               NOT NULL
        COMMENT '支付渠道规则',
    title            VARCHAR(64)          NOT NULL
        COMMENT '支付渠道名称',
    payment_way_code VARCHAR(32)          NOT NULL
        COMMENT '支付渠道码',
    enabled          TINYINT DEFAULT 1    NOT NULL
        COMMENT '启用状态[0:为禁用状态;1:为正常状态]',
    sort             INT     DEFAULT 1000 NOT NULL
        COMMENT '排序, 从大到小降序',
    created_at       datetime(6)          not null,
    updated_at       datetime(6)          null,
    UNIQUE KEY (rule_id, payment_way_code),
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[支付网关] 支付渠道规则对应的支付渠道表';

