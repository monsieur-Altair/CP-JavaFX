#DROP TABLE IF EXISTS `manufacturer`;
CREATE TABLE IF NOT EXISTS `manufacturer`(
    `id`        int         NOT NULL auto_increment,
    `name`      varchar(45) NOT NULL,
    `country`   varchar(45) NOT NULL,
    `email`     varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;
