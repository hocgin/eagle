-- auto-generated definition
create table t_notify
(
    id              bigint      not null
        primary key,
    actor           bigint      not null comment '触发者ID',
    notify_type     int         not null comment '通知类型',
    subject_type    int         null comment '订阅对象类型',
    subject_id      bigint      null comment '订阅对象ID',
    last_updater    bigint      null,
    last_updated_at datetime(6) null,
    creator         bigint      not null,
    created_at      datetime(6) not null
)
    comment '通知表';

-- auto-generated definition
create table t_notification
(
    notify_id bigint      not null comment '通知ID',
    receiver  bigint      not null comment '接收人ID',
    read_at   datetime(6) null,
    primary key (notify_id, receiver)
)
    comment '通知-接收人';

-- auto-generated definition
create table t_subscription
(
    id              bigint      not null
        primary key,
    subscriber      bigint      not null comment '订阅人ID',
    notify_type     int         not null comment '订阅通知类型',
    subject_id      bigint      not null comment '订阅对象ID',
    subject_type    int         not null comment '订阅对象类型',
    creator         bigint      not null,
    created_at      datetime(6) null,
    last_updated_at datetime(6) null,
    last_updater    bigint      null
)
    comment '订阅列表';

