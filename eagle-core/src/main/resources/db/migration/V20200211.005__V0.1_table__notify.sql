-- auto-generated definition
CREATE TABLE t_notify
(
    id              bigint,
    content         varchar(255) comment '消息文本',
    actor_id        bigint      not null comment '触发者ID',
    notify_type     int         not null comment '通知类型',
    subject_type    int         null comment '订阅对象类型',
    subject_id      bigint      null comment '订阅对象ID',
    creator         bigint      not null,
    created_at      datetime(6) not null,
    last_updater    bigint      null,
    last_updated_at datetime(6) null,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[消息模块] 通知表';

-- auto-generated definition
CREATE TABLE t_notification
(
    notify_id   bigint      not null comment '通知ID',
    receiver_id bigint      not null comment '接收人ID',
    read_at     datetime(6) null,
    PRIMARY KEY (notify_id, receiver_id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[消息模块] 通知-接收人表';

-- auto-generated definition
CREATE TABLE t_subscription
(
    id              bigint,
    subscriber_id   bigint      not null comment '订阅人ID',
    notify_type     int         not null comment '订阅通知类型',
    subject_id      bigint      not null comment '订阅对象ID',
    subject_type    int         not null comment '订阅对象类型',
    creator         bigint      not null,
    created_at      datetime(6) not null,
    last_updated_at datetime(6) null,
    last_updater    bigint      null,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT '[消息模块] 订阅列表';

