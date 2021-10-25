SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `manufacturer`;

CREATE TABLE IF NOT EXISTS `manufacturer`(
    `id_manufacturer`   int NOT NULL auto_increment,
    `name`      varchar(45) NOT NULL,
    `country`   varchar(45) NOT NULL,
    `email`     varchar(45) NOT NULL,
    `rating`    double  NOT NULL,
    `number_of_ratings` int  NOT NULL,
    PRIMARY KEY (`id_manufacturer`)
) DEFAULT CHARSET=utf8;

INSERT INTO `manufacturer` VALUES
    (1,'Intel','США','Intel@gmail.com',8.33,6),
    (2,'AMD','США','AMD@gmail.com',9.78,19),
    (3,'Nvidia','США','Nvidia@gmail.com',8.84,19),
    (4,'Samsung','Корея','Samsung@gmail.com',7.56,69),
    (5,'Crucial','США','Crucial@gmail.com',8.4,40);
SET FOREIGN_KEY_CHECKS = 1;

