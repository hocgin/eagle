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
    COMMENT ='[微信模块] 微信公众号配置表';

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
    COMMENT ='[微信模块] 微信用户表';
-- ################################################################################################
DROP TABLE IF EXISTS `wx_menu`;
CREATE TABLE `wx_menu`
(
    id              BIGINT AUTO_INCREMENT,
    appid           VARCHAR(32)         NOT NULL
        COMMENT 'APP ID',
    --
    title           VARCHAR(255)        NOT NULL
        COMMENT '标题',
    menu_type       TINYINT(1) UNSIGNED NOT NULL DEFAULT 1
        COMMENT '菜单类型, 0->通用菜单; 1->个性化菜单',
    menu_id         VARCHAR(32)
        COMMENT '上传个性化菜单后的 menu_id (仅菜单类型为: 个性化菜单)',
    button          JSON                NOT NULL
        COMMENT '菜单对象数组',
    match_rule      JSON
        COMMENT '菜单匹配规则(仅菜单类型为: 个性化菜单)',
    enabled         TINYINT(1) UNSIGNED NOT NULL DEFAULT 1
        COMMENT '启用状态[0:为禁用状态;1:为正常状态]',
    --
    uploader        BIGINT              NULL,
    uploaded_at     DATETIME(6)         NULL,
    creator         BIGINT              NOT NULL,
    created_at      DATETIME(6)         NOT NULL,
    last_updater    BIGINT              NULL,
    last_updated_at DATETIME(6)         NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='[微信模块] 微信菜单表';

DROP TABLE IF EXISTS `wx_material`;
CREATE TABLE `wx_material`
(
    id               BIGINT AUTO_INCREMENT,
    appid            VARCHAR(32)         NOT NULL
        COMMENT 'APP ID',
    --
    material_type    TINYINT(1) UNSIGNED NOT NULL DEFAULT 1
        COMMENT '素材类型: 0->图片; 1->语音; 2->视频; 3->缩略图; 4->图文',
    material_id      VARCHAR(32)
        COMMENT '素材ID，上传后获得',
    material_content JSON                NOT NULL
        COMMENT '素材内容',
    material_result  JSON                NOT NULL
        COMMENT '素材上传后获得',
    --
    creator          BIGINT              NOT NULL,
    created_at       DATETIME(6)         NOT NULL,
    last_updater     BIGINT              NULL,
    last_updated_at  DATETIME(6)         NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='[微信模块] 微信素材库';
-- ################################################################################################
DROP TABLE IF EXISTS `wx_mp_reply_rule`;
CREATE TABLE `wx_mp_reply_rule`
(
    `id`              BIGINT AUTO_INCREMENT,
    `appid`           VARCHAR(32)         NOT NULL
        COMMENT '开发者ID(AppID)',
    `title`           VARCHAR(32)         NOT NULL
        COMMENT '规则名称',
    `enabled`         TINYINT(1) UNSIGNED NOT NULL DEFAULT 1
        COMMENT '启用状态(0:为禁用状态;1:为正常状态)',

    `match_msg_type`  TINYINT(1) UNSIGNED NOT NULL DEFAULT 1
        COMMENT '匹配消息类型',
    `match_rule`      JSON                NOT NULL
        COMMENT '匹配规则',

    `reply_msg_type`  TINYINT(1) UNSIGNED NOT NULL DEFAULT 1
        COMMENT '回复消息类型',
    `reply_content`   JSON                NOT NULL
        COMMENT '回复内容',
    `sort`            INT(10)             NOT NULL DEFAULT 1000
        COMMENT '排序, 从大到小降序',
    --
    `created_at`      DATETIME(6)         NOT NULL
        COMMENT '创建时间',
    `creator`         BIGINT              NOT NULL
        COMMENT '创建者',
    `last_updated_at` DATETIME(6)
        COMMENT '更新时间',
    `last_updater`    BIGINT
        COMMENT '更新者',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='[微信模块] 微信自动回复配置表';
-- 被关注自动回复
-- 默认自动回复
-- 关键词自动回复
-- #[消息模版]###########################################################################################
DROP TABLE IF EXISTS `wx_mp_message_template`;
CREATE TABLE `wx_mp_message_template`
(
    `id`               BIGINT AUTO_INCREMENT,
    `appid`            VARCHAR(32)   NOT NULL
        COMMENT '开发者ID(AppID)',
    `template_id`      VARCHAR(64)   NOT NULL
        COMMENT '模版ID',
    `title`            VARCHAR(32)   NOT NULL
        COMMENT '模版标题',
    `primary_industry` VARCHAR(32)   NOT NULL
        COMMENT '模板所属行业的一级行业',
    `deputy_industry`  VARCHAR(32)   NOT NULL
        COMMENT '模板所属行业的二级行业',
    `content`          VARCHAR(1024) NOT NULL
        COMMENT '模板内容',
    `example`          VARCHAR(1024) NOT NULL
        COMMENT '模板示例',
    --
    `created_at`       DATETIME(6)   NOT NULL
        COMMENT '创建时间',
    `creator`          BIGINT        NOT NULL
        COMMENT '创建者',
    `last_updated_at`  DATETIME(6)
        COMMENT '更新时间',
    `last_updater`     BIGINT
        COMMENT '更新者',
    UNIQUE KEY (`appid`, `template_id`),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='[微信模块] 消息模版表';
-- #[微信用户标签]###########################################################################################
DROP TABLE IF EXISTS `wx_mp_user_tags`;
CREATE TABLE `wx_mp_user_tags`
(
    `id`     BIGINT AUTO_INCREMENT,
    `appid`  VARCHAR(32) NOT NULL
        COMMENT '开发者ID(AppID)',
    `tag_id` INT(10)     NOT NULL
        COMMENT '标签ID(来着微信)',
    `name`   VARCHAR(32) NOT NULL
        COMMENT '标签名称(来着微信)',
    UNIQUE KEY (`appid`, `tag_id`),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='[微信模块] 用户标签表';
DROP TABLE IF EXISTS `wx_mp_user_tags_relation`;
CREATE TABLE `wx_mp_user_tags_relation`
(
    `id`      BIGINT AUTO_INCREMENT,
    `tags_id` BIGINT      NOT NULL
        COMMENT '用户标签ID(来着自己)',
    `openid`  VARCHAR(32) NOT NULL
        COMMENT '用户的标识，对当前公众号唯一',
    UNIQUE KEY (`tags_id`, `openid`),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='[微信模块] 标签x用户表';
-- #[短链接]###########################################################################################
DROP TABLE IF EXISTS `wx_mp_short_url`;
CREATE TABLE `wx_mp_short_url`
(
    `id`         BIGINT AUTO_INCREMENT,
    `appid`      VARCHAR(32)  NOT NULL
        COMMENT '开发者ID(AppID)',
    `long_url`   VARCHAR(255) NOT NULL
        COMMENT '长链接',
    `short_url`  VARCHAR(255) NOT NULL
        COMMENT '短链接',
    # --
    `created_at` DATETIME(6)  NOT NULL
        COMMENT '创建时间',
    `creator`    BIGINT       NOT NULL
        COMMENT '创建者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='[微信模块] 短链接表';

DROP TABLE IF EXISTS `wx_mp_qrcode`;
CREATE TABLE `wx_mp_qrcode`
(
    `id`             BIGINT AUTO_INCREMENT,
    `appid`          VARCHAR(32) NOT NULL
        COMMENT '开发者ID(AppID)',
    `expire_seconds` INT(16)     NOT NULL
        COMMENT '过期时间(秒), -1 为永久型',
    `scene_id`       INT(16)
        COMMENT '场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000',
    `scene_str`      VARCHAR(64)
        COMMENT '场景值ID（字符串形式的ID），字符串类型，长度限制为1到64',
    `ticket`         VARCHAR(128)
        COMMENT '获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码',
    `url`            VARCHAR(64)
        COMMENT '二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片',
    `expire_at`      DATETIME(6)
        COMMENT '过期时间',
    # --
    `created_at`     DATETIME(6) NOT NULL
        COMMENT '创建时间',
    `creator`        BIGINT      NOT NULL
        COMMENT '创建者',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='[微信模块] 二维码表';
