#DROP TABLE IF EXISTS `purchase`;
CREATE TABLE IF NOT EXISTS `purchase`(
    `id`              int NOT NULL auto_increment,
    `id_user`         int NOT NULL,
    `id_manufacturer` int NOT NULL,
    `id_product`      int NOT NULL,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;
