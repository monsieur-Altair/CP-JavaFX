#DROP TABLE IF EXISTS `review`;
CREATE TABLE IF NOT EXISTS `review`(
   `id_review`        int NOT NULL auto_increment,
   `id_user`          int NOT NULL,
   `id_product`  int NOT NULL,
   `review_text`      varchar(45) NOT NULL,
   PRIMARY KEY (`id_review`),
   FOREIGN KEY (`id_user`)
       REFERENCES user(id_user)
       ON DELETE CASCADE,
   FOREIGN KEY (`id_product`)
       REFERENCES product(id_product)
       ON DELETE CASCADE
) DEFAULT CHARSET=utf8;