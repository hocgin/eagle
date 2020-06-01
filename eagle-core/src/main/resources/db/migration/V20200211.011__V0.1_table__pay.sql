DROP TABLE IF EXISTS `pay_log_data`;
CREATE TABLE `pay_log_data`
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

DROP TABLE IF EXISTS `pay_notify_app_log`;
CREATE TABLE `pay_notify_app_log`
(
    id               bigint auto_increment,
    app_id           bigint                           not null
        comment '接入方应用ID',
    pay_method_id    BIGINT           default '0'     not null
        comment '支付方式',
    pay_channel      varchar(64)                      not null
        comment '支付渠道',
    transaction_sn   varchar(64)                      not null
        comment '交易流水号',
    transaction_code varchar(64)                      not null
        comment '支付成功时，该笔交易的 code',
    sign_type        varchar(10)      default 'RSA'   not null
        comment '采用的签名方式: MD5 RSA RSA2 HASH-MAC等',
    input_charset    char(5)          default 'UTF-8' not null
        comment '字符编码',
    total_fee        int unsigned     default '0'     not null
        comment '涉及的金额，无小数',
    scale            tinyint unsigned default '0'     not null
        comment '小数位数',
    trade_no         varchar(64)                      not null
        comment '第三方交易号',
    notify_type      varchar(10)      default 'paid'  not null
        comment '通知类型，paid/refund/canceled',
    notify_status    varchar(7)       default 'INIT'  not null
        comment '通知支付调用方结果: INIT=>初始化; PENDING=>进行中; SUCCESS=>成功; FAILED=>失败',
    payment_at       datetime(6)
        comment '支付时间',
    created_at        datetime(6)                      not null,
    updated_at        datetime(6),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 所有通知应用方日志表';

DROP TABLE IF EXISTS `pay_refund`;
CREATE TABLE `pay_refund`
(
    id             bigint auto_increment,
    app_id         bigint                         not null
        comment '接入方应用ID',
    app_refund_sn  varchar(64)                    not null
        comment '接入方应用退款编号',
    transaction_sn varchar(64)                    not null
        comment '交易流水号',
    pay_method_id  BIGINT                         not null
        comment '支付方式',
    pay_channel    varchar(64)                    not null
        comment '选择的支付渠道，比如: 支付宝中的花呗、信用卡等',
    trade_no       varchar(64)                    not null
        comment '第三方交易号',
    refund_no      varchar(64)                    not null
        comment '支付平台生成的唯一退款单号',
    refund_fee     int unsigned     default '0'   not null
        comment '退款金额',
    scale          tinyint unsigned default '0'   not null
        comment '小数位数',
    refund_reason  varchar(255)                   not null
        comment '退款理由',
    currency_code  char(3)          default 'CNY' not null
        comment '币种: CNY USD HKD',
    refund_type    tinyint unsigned default '0'   not null
        comment '退款类型: 0=>业务退款; 1=>重复退款',
    refund_method  tinyint unsigned default '1'   not null
        comment '退款方式: 1=>自动原路返回; 2=>人工打款',
    refund_status  tinyint unsigned default '0'   not null
        comment '退款状态: 0=>未退款; 1=>退款处理中; 2=>退款成功; 3=>退款失败',
    created_at     datetime(6)                    not null
        comment '创建时间',
    updated_at     datetime(6)
        comment '更新时间',
    created_ip     varchar(32)
        comment '创建ip',
    update_ip      varchar(32)
        comment '请求源ip',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 退款记录表';

DROP TABLE IF EXISTS `pay_repeat_transaction`;
CREATE TABLE `pay_repeat_transaction`
(
    id               bigint auto_increment,
    app_id           varchar(32)                    not null
        comment '接入方应用ID',
    transaction_sn   varchar(64)                    not null
        comment '交易流水号',
    transaction_code varchar(64)                    not null
        comment '支付成功时，该笔交易的 code',
    pay_method_id    int unsigned     default '0'   not null
        comment '支付方式',
    trade_no         varchar(64)                    not null
        comment '第三方对应的交易号',
    total_fee        int unsigned     default '0'   not null
        comment '交易金额',
    scale            tinyint unsigned default '0'   not null
        comment '小数位数',
    currency_code    char(3)          default 'CNY' not null
        comment '支付选择的币种，CNY、HKD、USD等',
    repeat_type      tinyint unsigned default '1'   not null
        comment '重复类型: 1=>同渠道支付; 2=>不同渠道支付',
    repeat_status    tinyint unsigned default '0'   not null
        comment '处理状态: 0=>未处理; 1=>已处理',
    created_at       datetime(6)                    not null
        comment '创建时间',
    payment_at       datetime(6)
        comment '第三方交易时间',
    updated_at       datetime(6)
        comment '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 记录重复支付表';

DROP TABLE IF EXISTS `pay_transaction`;
CREATE TABLE `pay_transaction`
(
    id               bigint auto_increment,
    app_id           BIGINT                           not null
        comment '接入方应用ID',
    app_order_sn     varchar(64)                      not null
        comment '接入方应用订单号',
    pay_method_id    int unsigned     default '0'     not null
        comment '支付方式id，可以用来识别支付，如：支付宝、微信、PayPal等',
    pay_channel      varchar(64)                      not null
        comment '选择的支付渠道，比如：支付宝中的花呗、信用卡等',
    transaction_sn   varchar(64)                      not null
        comment '交易流水号',
    transaction_code varchar(64)
        comment '真实给第三方的交易code，异步通知的时候更新',
    total_fee        int unsigned     default '0'     not null
        comment '支付金额，整数方式保存',
    scale            tinyint unsigned default '0'     not null
        comment '金额对应的小数位数',
    currency_code    char(3)          default 'CNY'   not null
        comment '交易的币种',
    return_url       varchar(255)                     not null
        comment '支付后跳转url',
    notify_url       varchar(255)                     not null
        comment '支付后，异步通知url',
    email            varchar(64)                      not null
        comment '用户的邮箱',
    sign_type        varchar(10)      default 'RSA'   not null
        comment '采用的签方式：MD5 RSA RSA2 HASH-MAC等',
    input_charset    char(5)          default 'UTF-8' not null
        comment '字符集编码方式',
    trade_no         varchar(64)                      not null
        comment '第三方的流水号',
    order_status     tinyint          default 0       not null
        comment '订单状态: 0=>等待支付，1=>待付款完成， 2=>完成支付，3=>交易已关闭，-1=>支付失败',
    created_at       datetime(6)                      not null
        comment '创建时间',
    notify_at        datetime(6)
        comment '收到异步通知的时间',
    finish_at        datetime(6)
        comment '通知上游系统的时间',
    expire_at        datetime(6)
        comment '订单过期时间',
    payment_at       datetime(6)
        comment '第三方支付成功的时间',
    updated_at       datetime(6)
        comment '更新时间',
    created_ip       varchar(32)
        comment '创建的ip，这可能是自己服务的ip',
    updated_ip       varchar(32)
        comment '更新的ip',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 发起支付的数据表';

DROP TABLE IF EXISTS `pay_transaction_extension`;
CREATE TABLE `pay_transaction_extension`
(
    id               bigint auto_increment,
    transaction_sn   varchar(64)                  not null
        comment '交易流水号',
    transaction_code varchar(64)                  not null
        comment '生成传输给第三方的订单号',
    pay_method_id    int unsigned     default '0' not null
        comment '支付方式id，可以用来识别支付，如：支付宝、微信、PayPal等',
    call_num         tinyint unsigned default '0' not null
        comment '发起调用的次数',
    extension_data   text
        comment '扩展内容，需要保存：transaction_code 与 trade no 的映射关系，异步通知的时候填充',
    created_at       datetime(6)                  not null
        comment '创建时间',
    created_ip       varchar(32)
        comment '创建ip',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT = '[支付网关] 交易扩展数据表';
