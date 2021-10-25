#DROP TABLE IF EXISTS `purchase`;

CREATE TABLE IF NOT EXISTS `purchase`(
    `id_purchase`              int NOT NULL auto_increment,
    `id_user`         int NOT NULL,
    `id_manufacturer` int NOT NULL,
    `id_product`      int NOT NULL,
    PRIMARY KEY (`id_purchase`),
    FOREIGN KEY (`id_user`)
        REFERENCES user(id_user)
        ON DELETE CASCADE,
    FOREIGN KEY (`id_manufacturer`)
        REFERENCES manufacturer(id_manufacturer)
        ON DELETE CASCADE,
    FOREIGN KEY (`id_product`)
        REFERENCES product(id_product)
        ON DELETE CASCADE
) DEFAULT CHARSET=utf8;
