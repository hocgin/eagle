DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`
(
    id                      BIGINT AUTO_INCREMENT,
    --
    account_id              BIGINT         NOT NULL
        COMMENT '账号ID',
    order_sn                VARCHAR(64)    NOT NULL
        COMMENT '订单编号',
    coupon_id               BIGINT         NULL
        COMMENT '优惠券ID',

    coupon_amount           decimal(10, 2) NULL
        COMMENT '优惠券抵扣金额',
    freight_amount          DECIMAL(10, 2) NOT NULL DEFAULT 0
        COMMENT '运费金额',
    discount_amount         DECIMAL(10, 2) NOT NULL DEFAULT 0
        COMMENT '管理员后台调整订单使用的折扣金额',
    total_amount            DECIMAL(10, 2) NOT NULL
        COMMENT '[计算型]订单总金额',
    pay_amount              DECIMAL(10, 2) NOT NULL
        COMMENT '[计算型]应付金额（实际支付金额）',

    auto_confirm_day        INT            NULL
        COMMENT '自动确认时间（天）',
    pay_type                INT(1)
        COMMENT '支付方式：[0:未支付；1:支付宝；2:微信]',
    source_type             INT(1)         NOT NULL
        COMMENT '订单来源：[0:PC订单；1:APP订单]',
    order_status            INT(1)         NOT NULL
        COMMENT '订单状态：[0:待付款；1:待发货；2:已发货；3:已完成；4:已关闭；5:无效订单]',
    confirm_status          INT(1)         NOT NULL DEFAULT 0
        COMMENT '确认收货状态：[0:未确认；1:已确认]',
    delete_status           INT(1)         NOT NULL DEFAULT 0
        COMMENT '删除状态：[0:未删除；1:已删除]',
    receiver_name           VARCHAR(100)   NOT NULL
        COMMENT '收货人姓名',
    receiver_phone          VARCHAR(32)    NOT NULL
        COMMENT '收货人电话',
    receiver_post_code      VARCHAR(32)    NULL
        COMMENT '收货人邮编',
    receiver_province       VARCHAR(32)    NULL
        COMMENT '省份/直辖市',
    receiver_city           VARCHAR(32)    NULL
        COMMENT '城市',
    receiver_region         VARCHAR(32)    NULL
        COMMENT '区',
    receiver_detail_address VARCHAR(200)   NULL
        COMMENT '详细地址',
    remark                  VARCHAR(500)   NULL
        COMMENT '订单备注',
    payment_time            DATETIME(6)    NULL COMMENT '支付时间',
    delivery_time           DATETIME(6)    NULL COMMENT '发货时间',
    receive_time            DATETIME(6)    NULL COMMENT '确认收货时间',
    comment_time            DATETIME(6)    NULL COMMENT '评价时间',
    --
    creator                 BIGINT         NOT NULL,
    created_at              DATETIME(6)    NOT NULL,
    last_updater            BIGINT         NULL,
    last_updated_at         DATETIME(6)    NULL,
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[订单模块] 订单主表';

--
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`
(
    id                BIGINT AUTO_INCREMENT,
    --
    order_id          BIGINT         NULL COMMENT '订单ID',
    product_id        BIGINT         NULL comment '商品ID',
    product_pic       VARCHAR(500)   NULL COMMENT '商品主图',
    product_name      VARCHAR(200)   NULL COMMENT '商品名称',
    product_price     DECIMAL(10, 2) NULL COMMENT '销售价格',
    product_quantity  INT            NULL COMMENT '购买数量',
    product_sku_id    BIGINT         NULL COMMENT '商品SKU ID',
    product_sku_code  VARCHAR(50)    NULL COMMENT '商品SKU条码',
    coupon_amount     DECIMAL(10, 2) NULL COMMENT '优惠券优惠分解金额',
    real_amount       DECIMAL(10, 2) NULL COMMENT '[计算型]该商品经过优惠后的分解金额',
    product_spec_data VARCHAR(500)   NULL
        COMMENT '商品规格:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]',
    --
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[订单模块] 订单商品表';
--
DROP TABLE IF EXISTS `oms_order_return_apply`;
CREATE TABLE `oms_order_return_apply`
(
    `id`                 bigint(20)  NOT NULL AUTO_INCREMENT,
    `apply_status`       int(1)         DEFAULT NULL COMMENT '申请状态：[0:待处理；1:退货中；2:已完成；3:已拒绝]',
    -- #退货信息
    `order_item_id`      bigint(20)     DEFAULT NULL COMMENT '订单商品ID',
    `product_pic`        varchar(500)   DEFAULT NULL COMMENT '商品图片',
    `product_name`       varchar(200)   DEFAULT NULL COMMENT '商品名称',
    `product_spec_data`  varchar(500)   DEFAULT NULL COMMENT '商品规格',
    `product_price`      decimal(10, 2) DEFAULT NULL COMMENT '商品单价',
    `product_real_amount` decimal(10, 2) DEFAULT NULL COMMENT '商品实际支付单价',
    `return_quantity`    int(11)        DEFAULT NULL COMMENT '退货数量',
    `return_amount`      decimal(10, 2) DEFAULT NULL COMMENT '退款金额',
    `return_reason`      varchar(200)   DEFAULT NULL COMMENT '退货原因',
    `return_remark`      varchar(200)   DEFAULT NULL COMMENT '退货备注',
    -- #处理信息
    company_address_id   bigint(20)     DEFAULT NULL COMMENT '仓库收货地址表ID',
    handler              BIGINT      NULL COMMENT '处理人',
    handle_at            DATETIME(6) NULL COMMENT '处理时间',
    handle_remark        varchar(512)   DEFAULT NULL COMMENT '收货备注',
    receiver             BIGINT      NULL COMMENT '收货人',
    receive_at           DATETIME(6) NULL COMMENT '收货时间',
    receive_remark       varchar(512)   DEFAULT NULL COMMENT '收货备注',
    --
    creator              BIGINT      NOT NULL,
    created_at           DATETIME(6) NOT NULL,
    last_updater         BIGINT      NULL,
    last_updated_at      DATETIME(6) NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 27
  DEFAULT CHARSET = utf8 COMMENT ='订单退货申请';
