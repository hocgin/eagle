# 测试公众号
INSERT INTO wx_mp_config(`appid`, `title`, `app_secret`, `token`, `aes_key`, `enabled`, `creator`, `created_at`)
    VALUE ('wx6c554c6d25b19c4a', '测试公众号', 'fe8e31b3bc4038ae5b0870a94fa5ed77', 'wx0hocgin',
           'fbAmohv6UZyRYtAk2qogmgT4EKEkw4huSMLmAYFQSld', 1, 1, NOW());

# 网关授权应用
INSERT INTO bmw_payment_app (app_sn, title, enabled, created_at, created_ip)
VALUES (1, '测试', 1, '2020-06-12 20:31:26.000000', null);


# 支付平台
INSERT INTO bmw_payment_platform (platform_appid, title, platform_type, enabled, created_at, created_ip)
VALUES ('2016080300154586', '支付宝', 1, 1, '2020-06-12 21:02:44.000000', null);

