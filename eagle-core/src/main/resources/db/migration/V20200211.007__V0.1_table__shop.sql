DROP TABLE IF EXISTS `shop_product`;
CREATE TABLE `shop_product`
(
    id              BIGINT AUTO_INCREMENT,
    classify_id     BIGINT       NOT NULL
        COMMENT '产品分类(t_product_classify)ID',
    --
    video_url       VARCHAR(255)
        COMMENT 'video url',
    procurement     VARCHAR(255) NOT NULL
        COMMENT '采购地(中国,福建)',
    title           VARCHAR(255) NOT NULL
        COMMENT '产品名',
    attrs           VARCHAR(2024)
        COMMENT '产品属性: {}',
    status          tinyint      NOT NULL DEFAULT 1
        COMMENT '上架状态: [下架, 上架]',
    --
    creator         bigint       not null,
    created_at      datetime(6)  not null,
    last_updater    bigint       null,
    last_updated_at datetime(6)  null,
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[Shop模块] 商品表';
--
DROP TABLE IF EXISTS `shop_product_classify`;
CREATE TABLE shop_product_classify
(
    id              bigint AUTO_INCREMENT,
    parent_id       bigint,
    --
    title           varchar(255) NOT NULL
        COMMENT '产品分类名称',
    tree_path       varchar(255) NOT NULL
        COMMENT '树路径，组成方式: /父路径/当前ID',
    enabled         TINYINT(1) UNSIGNED   DEFAULT 1
        COMMENT '启用状态[0:为禁用状态;1:为正常状态]',
    sort            INT(10)      NOT NULL DEFAULT 1000
        COMMENT '排序, 从大到小降序',
    --
    creator         bigint       not null,
    created_at      datetime(6)  not null,
    last_updater    bigint       null,
    last_updated_at datetime(6)  null,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[Shop模块] 商品分类表';
--
DROP TABLE IF EXISTS `shop_sku`;
CREATE TABLE `shop_sku`
(
    id        BIGINT AUTO_INCREMENT,
    --
    sku       VARCHAR(126)   NOT NULL
        COMMENT 'SKU 编码',
    price     DECIMAL(10, 2) NOT NULL
        COMMENT '价格(如: 12.00)',
    inventory int(10)        NOT NULL DEFAULT 0
        COMMENT '库存, 默认为0',
    image_url VARCHAR(255)
        COMMENT '该特色商品图片',
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[Shop模块] 商品SKU表';
--
DROP TABLE IF EXISTS `shop_spec`;
CREATE TABLE `shop_spec`
(
    id         BIGINT AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    --
    title      VARCHAR(255)
        COMMENT '规格名称(如: 颜色)',
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[Shop模块] 商品规格表';
--
DROP TABLE IF EXISTS `shop_spec_value`;
CREATE TABLE `shop_spec_value`
(
    id      BIGINT AUTO_INCREMENT,
    spec_id BIGINT NOT NULL
        COMMENT '规格(t_spec)ID',
    sku_id  BIGINT NOT NULL
        COMMENT '规格(t_sku)ID',
    --
    value   VARCHAR(255)
        COMMENT '规格值(如: 红色)',
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[Shop模块] 商品规格值表';
