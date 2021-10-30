SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `product`;

CREATE TABLE IF NOT EXISTS `product`(
    `id_product` int        NOT NULL auto_increment,
    `name`      varchar(45) NOT NULL,
    `type`      varchar(45) NOT NULL,
    `cost`      int         NOT NULL,
    `count`     int         NOT NULL,
    `id_manufacturer` int   NOT NULL,
    PRIMARY KEY (`id_product`),
    FOREIGN KEY (`id_manufacturer`)
        REFERENCES manufacturer(id_manufacturer)
        ON DELETE CASCADE
) DEFAULT CHARSET=utf8;

INSERT INTO `product` VALUES
      (1,'GeForce 3060','видеокарта',1200,23,3),
      (2,'GeForce 2060','видеокарта',880,65,3),
      (3,'GeForce 1080 Ti','видеокарта',900,54,3),
      (4,'i5-9900HF','процессор',340,87,1),
      (5,'i7-7900F','процессор',460,67,1),
      (6,'i9-9900HF','процессор',530,47,1),
      (7,'Ryzen 3','процессор',350,86,2),
      (8,'Ryzen 5','процессор',420,59,2),
      (9,'Ryzen 7','процессор',350,43,2),
      (10,'S24R350FHI','монитор',100,56,4),
      (11,'C25R365LCR','монитор',120,32,4),
      (12,'M34K343DFH','монитор',150,62,4),
      (13,'Fury Beast PC4-25600','память',150,62,5),
      (14,'Fury Beast PC4-21300','память',230,13,5),
      (15,'Fury Beast PC3-12800','память',190,34,5);

SET FOREIGN_KEY_CHECKS = 1;
