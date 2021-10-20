DROP TABLE IF EXISTS `rebate`;
CREATE TABLE IF NOT EXISTS `rebate`(
    `id_rebate`        int NOT NULL auto_increment,
    `id_user`          int NOT NULL,
    `percent`          int NOT NULL,
    `id_manufacturer`  int NOT NULL,
    PRIMARY KEY (`id_rebate`)
) DEFAULT CHARSET=utf8;
