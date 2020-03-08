CREATE TABLE t_comment_target
(
    id       BIGINT AUTO_INCREMENT,
    rel_id   BIGINT NOT NULL
        COMMENT '评论对象ID',
    rel_type BIGINT NOT NULL
        COMMENT '评论对象类型',
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[评论模块] 评论对象表';

-- auto-generated definition
CREATE TABLE t_comment
(
    id              bigint AUTO_INCREMENT,
    parent_id       bigint,
    target_id       bigint       NOT NULL
        COMMENT '评论对象ID',
    --
    tree_path       varchar(255) NOT NULL
        COMMENT '树路径，组成方式: /父路径/当前ID',
    content         varchar(255) NOT NULL
        COMMENT '评论内容',
    enabled         TINYINT(1) UNSIGNED DEFAULT 1
        COMMENT '启用状态[0:为禁用状态;1:为正常状态]',
    creator         bigint       not null
        comment '评论人',
    created_at      datetime(6)  not null,
    last_updater    bigint       null,
    last_updated_at datetime(6)  null,
    FOREIGN KEY (`target_id`) REFERENCES t_comment_target (`id`),
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[评论模块] 评论表';


