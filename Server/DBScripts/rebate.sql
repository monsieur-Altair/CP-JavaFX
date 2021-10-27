#DROP TABLE IF EXISTS `rebate`;
CREATE TABLE IF NOT EXISTS `rebate`(
    `id_rebate`        int NOT NULL auto_increment,
    `id_user`          int NOT NULL,
    `percent`          int NOT NULL,
    `id_manufacturer`  int NOT NULL,
    PRIMARY KEY (`id_rebate`),
    FOREIGN KEY (`id_manufacturer`)
        REFERENCES manufacturer(id_manufacturer)
        ON DELETE CASCADE

                                   #add another for key
) DEFAULT CHARSET=utf8;
