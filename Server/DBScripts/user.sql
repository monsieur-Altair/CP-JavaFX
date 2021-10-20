DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user`(
    `id`         int         NOT NULL auto_increment,
    `first_name` varchar(45) NOT NULL,
    `last_name`  varchar(45) NOT NULL,
    `password`   varchar(45) NOT NULL,
    `email`      varchar(45) NOT NULL,
    `address`    varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUE (1,'admin MAXON','12345','MAXON@gmail.com') ;
