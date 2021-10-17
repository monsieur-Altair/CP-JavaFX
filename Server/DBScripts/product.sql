#DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product`(
    `id`        int         NOT NULL auto_increment,
    `name`      varchar(45) NOT NULL,
    `cost`      int         NOT NULL,
    `count`     int         NOT NULL,###############
    `id_purchase`     int   NOT NULL,###############
    `id_manufacturer` int   NOT NULL,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;
