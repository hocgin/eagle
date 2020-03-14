DROP TABLE IF EXISTS `shop_product`;
CREATE TABLE `shop_product`
(
    id                  BIGINT AUTO_INCREMENT,
    product_category_id BIGINT       NOT NULL
        COMMENT '商品品类(t_product_classify)ID',
    --
    video_url           VARCHAR(255)
        COMMENT 'video url',
    procurement         VARCHAR(255) NOT NULL
        COMMENT '采购地(中国,福建)',
    title               VARCHAR(255) NOT NULL
        COMMENT '产品名',
    attrs               VARCHAR(1024)         DEFAULT NULL
        COMMENT '产品属性: {}',
    publish_status      tinyint      NOT NULL DEFAULT 1
        COMMENT '上架状态: [0:下架, 1:上架]',
    delete_status       tinyint      NOT NULL DEFAULT 0
        COMMENT '删除状态: [0:未删除, 1:删除]',
    unit                VARCHAR(16)           DEFAULT '件'
        COMMENT '单位',
    weight              DECIMAL(10, 2)        DEFAULT NULL
        COMMENT '商品重量(克)',
    --
    creator             bigint       not null,
    created_at          datetime(6)  not null,
    last_updater        bigint       null,
    last_updated_at     datetime(6)  null,
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[Shop模块] 商品表';
--
DROP TABLE IF EXISTS `shop_product_category`;
CREATE TABLE shop_product_category
(
    id              bigint AUTO_INCREMENT,
    parent_id       bigint,
    --
    title           varchar(255) NOT NULL
        COMMENT '商品品类名称',
    remark          varchar(1024)
        COMMENT '商品品类描述',
    image_url       varchar(255)
        COMMENT '商品品类图片',
    keywords        varchar(255)
        COMMENT '商品品类关键词',
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
    COMMENT '[Shop模块] 商品品类表';
--
DROP TABLE IF EXISTS `shop_sku`;
CREATE TABLE `shop_sku`
(
    id         BIGINT AUTO_INCREMENT,
    product_id BIGINT,
    --
    sku_code   VARCHAR(126)   NOT NULL
        COMMENT 'SKU 编码',
    price      DECIMAL(10, 2) NOT NULL
        COMMENT '商品价格(如: 12.00)',
    stock      int            NOT NULL DEFAULT 0
        COMMENT '库存, 默认为0',
    sale       int            NOT NULL DEFAULT 0
        COMMENT '销量, 默认为0',
    spec_data  varchar(512)            DEFAULT NULL
        COMMENT '规格属性(JSONArray, 如: [{"key":"颜色","value":"银色"}])',
    image_url  VARCHAR(255)
        COMMENT '该特色商品图片',
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[Shop模块] 商品SKU表';

-- 品类
INSERT INTO shop_product_category(id, title, tree_path, creator, created_at)
    VALUE (1, '测试品类', '/1', 1, NOW());
