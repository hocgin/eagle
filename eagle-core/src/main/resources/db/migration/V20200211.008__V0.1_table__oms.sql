DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`
(
    id                         BIGINT AUTO_INCREMENT,
    --
    account_id                 BIGINT         NOT NULL
        COMMENT '账号ID',
    order_sn                   VARCHAR(64)    NOT NULL
        COMMENT '订单编号',
    trade_sn                   VARCHAR(64)
        COMMENT '交易单号',
    coupon_account_id          BIGINT         NULL
        COMMENT '用户优惠券ID',

    coupon_discount_amount     decimal(10, 2) NOT NULL DEFAULT 0
        COMMENT '优惠券抵扣金额',
    adjustment_discount_amount DECIMAL(10, 2) NOT NULL DEFAULT 0
        COMMENT '管理员后台调整订单使用的折扣金额',
    freight_amount             DECIMAL(10, 2) NOT NULL DEFAULT 0
        COMMENT '运费金额',
    total_amount               DECIMAL(10, 2) NOT NULL
        COMMENT '[计算型]订单总金额',
    discount_amount            decimal(10, 2) NOT NULL DEFAULT 0
        COMMENT '优惠分解金额(不含后台调整优惠)',
    pay_amount                 DECIMAL(10, 2) NOT NULL
        COMMENT '[计算型]应付金额（实际支付金额）',

    auto_confirm_day           INT            NULL
        COMMENT '自动确认时间（天）',
    pay_type                   INT(1)
        COMMENT '支付方式：[0:未支付；1:支付宝；2:微信]',
    source_type                INT(1)         NOT NULL
        COMMENT '订单来源：[0:PC订单；1:APP订单]',
    order_status               INT(1)         NOT NULL
        COMMENT '订单状态：[0:待付款；1:待发货；2:已发货；3:已完成；4:已关闭；5:无效订单]',
    confirm_status             INT(1)         NOT NULL DEFAULT 0
        COMMENT '确认收货状态：[0:未确认；1:已确认]',
    delete_status              INT(1)         NOT NULL DEFAULT 0
        COMMENT '删除状态：[0:未删除；1:已删除]',
    receiver_name              VARCHAR(100)   NOT NULL
        COMMENT '收货人姓名',
    receiver_phone             VARCHAR(32)    NOT NULL
        COMMENT '收货人电话',
    receiver_post_code         VARCHAR(32)    NULL
        COMMENT '收货人邮编',
    receiver_ad_code           VARCHAR(32)    NULL
        COMMENT '收货人区域编码',
    receiver_province          VARCHAR(32)    NULL
        COMMENT '收货人省份/直辖市',
    receiver_city              VARCHAR(32)    NULL
        COMMENT '收货人城市',
    receiver_region            VARCHAR(32)    NULL
        COMMENT '收货人区',
    receiver_detail_address    VARCHAR(200)   NULL
        COMMENT '收货人详细地址',
    remark                     VARCHAR(500)   NULL
        COMMENT '订单备注',
    payment_time               DATETIME(6)    NULL COMMENT '支付时间',
    delivery_time              DATETIME(6)    NULL COMMENT '发货时间',
    receive_time               DATETIME(6)    NULL COMMENT '确认收货时间',
    comment_time               DATETIME(6)    NULL COMMENT '评价时间',
    --
    creator                    BIGINT         NOT NULL,
    created_at                 DATETIME(6)    NOT NULL,
    last_updater               BIGINT         NULL,
    last_updated_at            DATETIME(6)    NULL,
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[订单模块] 订单主表';

--
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`
(
    id                         BIGINT AUTO_INCREMENT,
    --
    order_id                   BIGINT         NULL COMMENT '订单ID',
    product_id                 BIGINT         NULL comment '商品ID',
    product_pic                VARCHAR(500)   NULL COMMENT '商品主图',
    product_name               VARCHAR(200)   NULL COMMENT '商品名称',
    product_price              DECIMAL(10, 2) NULL COMMENT '销售价格',
    product_quantity           INT            NULL COMMENT '购买数量',
    product_sku_id             BIGINT         NULL COMMENT '商品SKU ID',
    product_sku_code           VARCHAR(50)    NULL COMMENT '商品SKU条码',
    product_spec_data          JSON           NULL
        COMMENT '商品规格:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]',

    discount_amount            DECIMAL(10, 2) NOT NULL DEFAULT 0 COMMENT '优惠分解金额(不含后台调整)',
    adjustment_discount_amount DECIMAL(10, 2) NOT NULL DEFAULT 0 COMMENT '后台调整优惠',
    total_amount               DECIMAL(10, 2) NOT NULL COMMENT '[计算型]原总价=销售价格x购买数量',
    real_amount                DECIMAL(10, 2) NOT NULL COMMENT '[计算型]该商品经过优惠后的分解金额(实际支付金额)=原总价-后台调整优惠-优惠分解金额',
    --
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[订单模块] 订单商品表';
--
DROP TABLE IF EXISTS `oms_order_refund_apply`;
CREATE TABLE `oms_order_refund_apply`
(
    `id`               bigint(20)  NOT NULL AUTO_INCREMENT,
    apply_sn           VARCHAR(64) NOT NULL
        COMMENT '退款申请编号',
    `apply_status`     int(1)      NOT NULL DEFAULT 0 COMMENT '申请状态：[0:待处理；1:退货中；2:已完成；3:已拒绝]',
    -- #退货信息
    `order_item_id`    bigint(20)  NOT NULL COMMENT '订单商品ID',
    `refund_quantity`  int(11)              DEFAULT NULL COMMENT '退货数量',
    `refund_amount`    decimal(10, 2)       DEFAULT NULL COMMENT '退款金额',
    `refund_reason`    varchar(200)         DEFAULT NULL COMMENT '退货原因',
    `refund_remark`    varchar(200)         DEFAULT NULL COMMENT '退货备注',
    -- #处理信息
    company_address_id bigint(20)           DEFAULT NULL COMMENT '仓库收货地址表ID',
    handler            BIGINT      NULL COMMENT '处理人',
    handle_at          DATETIME(6) NULL COMMENT '处理时间',
    handle_remark      varchar(512)         DEFAULT NULL COMMENT '处理备注',
    receiver           BIGINT      NULL COMMENT '收货人',
    receive_at         DATETIME(6) NULL COMMENT '收货时间',
    receive_remark     varchar(512)         DEFAULT NULL COMMENT '收货备注',
    --
    creator            BIGINT      NOT NULL,
    created_at         DATETIME(6) NOT NULL,
    last_updater       BIGINT      NULL,
    last_updated_at    DATETIME(6) NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='订单退货申请';
--
DROP TABLE IF EXISTS `oms_cart_item`;
CREATE TABLE `oms_cart_item`
(
    id                    BIGINT AUTO_INCREMENT,
    --
    account_id            BIGINT         NOT NULL
        COMMENT '账号ID',
    product_id            BIGINT         NOT NULL
        COMMENT '商品ID',
    add_product_price     DECIMAL(10, 2) NOT NULL
        COMMENT '加入时，商品价格',
    add_product_title     VARCHAR(128)   NOT NULL
        COMMENT '加入时，商品标题',
    add_product_image_url VARCHAR(255)
        COMMENT '加入时，商品图片',
    sku_id                BIGINT         NOT NULL
        COMMENT 'SKU ID',
    sku_spec_data         JSON           NOT NULL
        COMMENT '规格属性',
    quantity              INT(8)         NOT NULL DEFAULT 1
        COMMENT '加入的数量',
    --
    creator               BIGINT         NOT NULL,
    created_at            DATETIME(6)    NOT NULL,
    last_updater          BIGINT         NULL,
    last_updated_at       DATETIME(6)    NULL,
    UNIQUE KEY (account_id, sku_id),
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[订单模块] 购物车表';
