DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file`
(
    id         BIGINT AUTO_INCREMENT,
    filename   VARCHAR(200)         NOT NULL
        COMMENT '文件名',
    file_url   VARCHAR(200)         NOT NULL
        COMMENT '链接地址',
    rel_id     BIGINT               NOT NULL
        COMMENT '业务ID',
    rel_type   INT(10)              NOT NULL
        COMMENT '业务类型',
    sort       INT(10) DEFAULT 1000 NOT NULL
        COMMENT '排序,默认:1000',
    created_at TIMESTAMP(6)         NOT NULL
        COMMENT '创建时间',
    creator    BIGINT               NOT NULL
        COMMENT '创建人',

    UNIQUE KEY (rel_id, rel_type),
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[基础模块] 文件引用表';
