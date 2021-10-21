DROP TABLE IF EXISTS `review`;
CREATE TABLE IF NOT EXISTS `review`(
   `id_review`        int NOT NULL auto_increment,
   `id_user`          int NOT NULL,
   `id_manufacturer`  int NOT NULL,
   PRIMARY KEY (`id_review`)
) DEFAULT CHARSET=utf8;