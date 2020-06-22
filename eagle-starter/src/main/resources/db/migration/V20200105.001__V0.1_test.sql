DROP TABLE IF EXISTS `t_example`;
CREATE TABLE `t_example`
(
    `id`         INT AUTO_INCREMENT,
    `name`       VARCHAR(255),
    `created_at` DATETIME(6) NOT NULL,
    PRIMARY KEY (`id`)
);
