DROP TABLE IF EXISTS `bmw_trade_log`;
CREATE TABLE `bmw_trade_log`
(
    id             bigint auto_increment,
    app_id         bigint      not null
        comment '接入方应用ID',
    app_order_sn   varchar(64) not null
        comment '接入方应用单号',
    transaction_sn varchar(64) not null
        comment '交易流水号',
    request_header text        not null
        comment '请求头',
    request_params text        not null
        comment '请求参数',
    log_type       varchar(10) not null
        comment '日志类型: payment=>支付; refund=>退款; notify=>异步通知; return=>同步通知; query=>查询',
    created_at     datetime(6) not null
        comment '创建时间',
    created_ip     varchar(32)
        comment '创建ip',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 所有和第三方支付交易日志表';

DROP TABLE IF EXISTS `bmw_payment_transaction`;
CREATE TABLE `bmw_payment_transaction`
(
    id             bigint auto_increment,
    app_id         bigint                       not null
        comment '接入应用',
    app_order_sn   varchar(64)                  not null
        comment '接入应用订单号',
    --
    transaction_sn varchar(64)                  not null
        comment '交易流水号',
    total_fee      decimal(10, 2) default '0'   not null
        comment '交易总金额',
    payment_way    bigint
        comment '支付方式(第三方回调填充)',
    trade_no       varchar(64)
        comment '第三方的流水号(第三方回调填充)',
    currency_code  char(3)        default 'CNY' not null
        comment '支付选择的币种，CNY、HKD、USD等',
    trade_status   tinyint        default 0     not null
        comment '交易状态: 0=>等待支付; 1=>待付款完成; 2=>完成支付; 3=>交易已关闭; -1=>支付失败',
    created_at     datetime(6)                  not null
        comment '创建时间',
    created_ip     varchar(32)
        comment '创建的IP',
    updated_at     datetime(6)
        comment '更新时间',
    updated_ip     varchar(32)
        comment '更新的IP',
    finish_at      datetime(6)
        comment '通知接入应用并完成交易的时间',
    expire_at      datetime(6)
        comment '订单过期时间',
    notify_at      datetime(6)
        comment '接收到第三方支付通知的时间',
    payment_at     datetime(6)
        comment '第三方支付成功的时间',
    UNIQUE KEY (transaction_sn),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 交易流水表';

DROP TABLE IF EXISTS `bmw_payment_record`;
CREATE TABLE `bmw_payment_record`
(
    id             bigint auto_increment,
    transaction_id bigint      not null
        comment '交易流水ID',
    payment_way    bigint      not null
        comment '支付方式',
    created_at     datetime(6) not null
        comment '创建时间',
    created_ip     varchar(32)
        comment '创建的IP',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 支付记录表';

DROP TABLE IF EXISTS `bmw_payment_refund`;
CREATE TABLE `bmw_payment_refund`
(
    id              bigint auto_increment,
    app_id          bigint                         not null
        comment '接入方应用ID',
    app_refund_sn   varchar(64)                    not null
        comment '接入方应用退款编号',
    --
    refund_sn       varchar(64)                    not null
        comment '退款流水编号',
    transaction_sn  varchar(64)                    not null
        comment '交易流水号',
    refund_trade_no varchar(64)                    not null
        comment '第三方退款的流水号',
    refund_fee      decimal(10, 2)   default '0'   not null
        comment '退款金额',
    refund_reason   varchar(255)
        comment '退款理由',
    payment_way     bigint
        comment '支付方式(第三方回调填充)',
    currency_code   char(3)          default 'CNY' not null
        comment '币种: CNY USD HKD',
    refund_status   tinyint unsigned default '0'   not null
        comment '退款状态: 0=>未退款; 1=>退款处理中; 2=>退款成功; 3=>退款失败',
    created_at      datetime(6)                    not null
        comment '创建时间',
    created_ip      varchar(32)
        comment '创建ip',
    updated_at      datetime(6)
        comment '更新时间',
    update_ip       varchar(32)
        comment '更新ip',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 退款记录表';

DROP TABLE IF EXISTS `bmw_notify_app_log`;
CREATE TABLE `bmw_notify_app_log`
(
    id                  bigint auto_increment,
    trade_no            varchar(64)                    not null
        comment '交易流水号/退款流水号',
    order_sn            varchar(64)                    not null
        comment '接入应用订单号/退款订单号',
    payment_way         bigint                         not null
        comment '支付方式',
    sign_type           varchar(10)      default 'RSA' not null
        comment '采用的签名方式: MD5 RSA RSA2 HASH-MAC等',
    total_fee           decimal(10, 2)   default '0'   not null
        comment '退款金额/支付金额',
    notify_event        tinyint unsigned default 0     not null
        comment '通知事件类型，0=>支付事件; 1=>退款事件; 2=>取消事件',
    notify_event_status tinyint unsigned default 0     not null
        comment '通知接入应用结果: 0=>初始化; 1=>进行中; 2=>成功; 3=>失败',
    finish_at           datetime(6)
        comment '完成通知时间',
    created_at          datetime(6)                    not null,
    updated_at          datetime(6),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 所有通知应用方日志表';

DROP TABLE IF EXISTS `bmw_log_data`;
CREATE TABLE `bmw_log_data`
(
    id             bigint auto_increment,
    app_id         bigint      not null
        comment '接入方应用ID',
    app_order_sn   varchar(64) not null
        comment '接入方应用订单号',
    transaction_sn varchar(64) not null
        comment '交易流水号',
    request_header text        not null
        comment '请求头',
    request_params text        not null
        comment '请求参数',
    log_type       varchar(10) not null
        comment '日志类型: payment=>支付; refund=>退款; notify=>异步通知; return=>同步通知; query=>查询',
    created_at     datetime(6) not null
        comment '创建时间',
    created_ip     varchar(32)
        comment '创建ip',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 所有交易日志表';
