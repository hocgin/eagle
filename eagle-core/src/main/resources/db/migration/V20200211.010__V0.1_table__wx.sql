DROP TABLE IF EXISTS `wx_mp_reply_rule`;
CREATE TABLE `wx_mp_reply_rule`
(
    `id`              BIGINT AUTO_INCREMENT,
    `appid`           VARCHAR(32)  NOT NULL
        COMMENT '开发者ID(AppID)',
    `title`           VARCHAR(32)  NOT NULL
        COMMENT '规则名称',
    `enabled`         TINYINT(1) UNSIGNED   DEFAULT 1
        COMMENT '启用状态(0:为禁用状态;1:为正常状态)',
    `reply_type`      TINYINT(1) UNSIGNED   DEFAULT 1
        COMMENT '回复类型',
    `reply_content`   VARCHAR(255)
        COMMENT '回复内容',
    `sort`            INT(10)      NOT NULL DEFAULT 1000
        COMMENT '排序, 从大到小降序',
    `keywords`        VARCHAR(255) NOT NULL
        COMMENT '关键词列表',
    `match_mode`      TINYINT(1) UNSIGNED   DEFAULT 1
        COMMENT '匹配模式: 0=>完全匹配, 1=>部分匹配',
    --
    `created_at`      DATETIME(6)  NOT NULL
        COMMENT '创建时间',
    `creator`         BIGINT       NOT NULL
        COMMENT '创建者',
    `last_updated_at` DATETIME(6)
        COMMENT '更新时间',
    `last_updater`    BIGINT
        COMMENT '更新者',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='微信自动回复配置表';
-- 被关注自动回复
-- 默认自动回复
-- 关键词自动回复

DROP TABLE IF EXISTS `wx_mp_config`;
CREATE TABLE `wx_mp_config`
(
    appid             VARCHAR(32)
        COMMENT '开发者ID(AppID)',
    title             VARCHAR(255) NOT NULL
        COMMENT '微信公众号标题',
    app_secret        VARCHAR(64)  NOT NULL
        COMMENT '开发者密码(AppSecret)',
    token             VARCHAR(64)  NOT NULL
        COMMENT '令牌(Token)',
    aes_key           VARCHAR(64)  NOT NULL
        COMMENT '消息加解密密钥(EncodingAESKey)',
    enabled           INT(1)       NOT NULL DEFAULT 0
        COMMENT '启用状态[0:为禁用状态;1:为正常状态]',
    --
    `created_at`      DATETIME(6)  NOT NULL
        COMMENT '创建时间',
    `creator`         BIGINT       NOT NULL
        COMMENT '创建者',
    `last_updated_at` DATETIME(6)
        COMMENT '更新时间',
    `last_updater`    BIGINT
        COMMENT '更新者',
    PRIMARY KEY (appid)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='[微信模块] 公众号配置表';

DROP TABLE IF EXISTS `wx_user`;
CREATE TABLE `wx_user`
(
    id              BIGINT AUTO_INCREMENT,
    openid          VARCHAR(32)
        COMMENT '用户的标识，对当前公众号唯一',
    account_id      BIGINT UNIQUE
        COMMENT '关联账号',
    appid           VARCHAR(32) NOT NULL
        COMMENT 'APPID',
    subscribe       INT(1)      NOT NULL
        COMMENT '用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。',
    nickname        VARCHAR(8)  NOT NULL
        COMMENT '用户的昵称',
    sex             INT         NOT NULL
        COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
    city            VARCHAR(8)
        COMMENT '用户所在城市',
    country         VARCHAR(8)
        COMMENT '用户所在国家',
    province        VARCHAR(8)
        COMMENT '用户所在省份',
    language        VARCHAR(8)
        COMMENT '用户的语言，简体中文为zh_CN',
    headimgurl      VARCHAR(512)
        COMMENT '用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。',
    subscribe_time  DATETIME(6)
        COMMENT '用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间',
    unionid         VARCHAR(8)
        COMMENT '只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。',
    remark          VARCHAR(255)
        COMMENT '公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注',
    subscribe_scene VARCHAR(32)
        COMMENT '返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他',
    qr_scene        VARCHAR(16)
        COMMENT '二维码扫码场景（开发者自定义）',
    qr_scene_str    VARCHAR(32)
        COMMENT '二维码扫码场景（开发者自定义）',
    `created_at`    DATETIME(6) NOT NULL,
    UNIQUE KEY (appid, openid),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='[微信模块] 用户表';


--
DROP TABLE IF EXISTS `wx_menu_match_rule`;
CREATE TABLE `wx_menu_match_rule`
(
    `id`                   BIGINT AUTO_INCREMENT,
    `appid`                VARCHAR(32) NOT NULL
        COMMENT '开发者ID(AppID)',
    `tag_id`               VARCHAR(16)
        COMMENT '用户标签的id，可通过用户标签管理接口获取',
    `sex`                  VARCHAR(16)
        COMMENT '性别：男（1）女（2），不填则不做匹配',
    `client_platform_type` VARCHAR(16)
        COMMENT 'IOS(1), Android(2),Others(3)，不填则不做匹配',
    `language`             VARCHAR(16)
        COMMENT '语言',
    `country`              VARCHAR(16)
        COMMENT '国家',
    `province`             VARCHAR(16)
        COMMENT '省份',
    `city`                 VARCHAR(16)
        COMMENT '城市',
    --
    creator             bigint       not null,
    created_at          datetime(6)  not null,
    last_updater        bigint       null,
    last_updated_at     datetime(6)  null,
    UNIQUE KEY (`tag_id`, `sex`, `client_platform_type`, `language`, `country`, `province`, `city`),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='[微信模块] 菜单匹配规则表';

DROP TABLE IF EXISTS `wx_menu`;
CREATE TABLE `wx_menu`
(
    id            BIGINT AUTO_INCREMENT,
    parent_id       bigint,
    --
    match_rule_id BIGINT      NOT NULL
        COMMENT '菜单匹配规则',
    name          VARCHAR(64) NOT NULL
        COMMENT '菜单标题，不超过16个字节，子菜单不超过60个字节',
    type          VARCHAR(16) NOT NULL
        COMMENT '菜单的响应动作类型，view表示网页类型，click表示点击类型，miniprogram表示小程序类型',
    `key`         VARCHAR(128)
        COMMENT 'click等点击类型必须，菜单KEY值，用于消息接口推送，不超过128字节',
    `url`         VARCHAR(1024)
        COMMENT 'view、miniprogram类型必须, 网页链接',
    `media_id`    VARCHAR(1024)
        COMMENT 'media_id类型和view_limited类型必须, 调用新增永久素材接口返回的合法 media_id',
    `appid`       VARCHAR(1024)
        COMMENT 'miniprogram类型必须, 小程序的appid（仅认证公众号可配置）',
    `pagepath`    VARCHAR(1024)
        COMMENT 'miniprogram类型必须, 小程序的页面路径',
    tree_path       varchar(255) NOT NULL
        COMMENT '树路径，组成方式: /父路径/当前ID',
    enabled         TINYINT(1) UNSIGNED   DEFAULT 1
        COMMENT '启用状态[0:为禁用状态;1:为正常状态]',
    sort            INT(10)      NOT NULL DEFAULT 1000
        COMMENT '排序, 从大到小降序',
    --
    creator             bigint       not null,
    created_at          datetime(6)  not null,
    last_updater        bigint       null,
    last_updated_at     datetime(6)  null,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='[微信模块] 菜单表';
--
