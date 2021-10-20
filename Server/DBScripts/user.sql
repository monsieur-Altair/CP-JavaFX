DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user`(
    `id_user`    int         NOT NULL auto_increment,
    `login`      varchar(45) NOT NULL,
    `password`   varchar(45) NOT NULL,
    `first_name` varchar(45) NOT NULL,
    `last_name`  varchar(45) NOT NULL,
    `money`      int         NOT NULL,
    `address`    varchar(45) NOT NULL,
    `phone`      varchar(45) NOT NULL,
    PRIMARY KEY (`id_user`)
) DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUE (1,'admin','Maxim','Melnikov','1242sda23',10000,'Minsk, Belarus','+375442343123');
