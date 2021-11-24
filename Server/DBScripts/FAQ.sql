SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `faq`;

CREATE TABLE IF NOT EXISTS `faq`(
     `id_faq`     int          NOT NULL auto_increment,
     `id_user`  int          NOT NULL,
     `question`   varchar(100) NOT NULL,
     `answer`     varchar(100) NOT NULL,
     PRIMARY KEY (`id_faq`),
     FOREIGN KEY (`id_user`)
         REFERENCES user(id_user)
         ON DELETE CASCADE
) DEFAULT CHARSET=utf8;

INSERT INTO `faq`
VALUES (1,3,'где просмотреть графики?','вкладка производители'),
       (2,2,'где пополнить счет?','вкладка профиль'),
       (3,4,'что отображает график?','процент продуктов производителя от всех на рынке'),
       (4,5,'сколько всего тем в приложении?','пока что 2: темная и светлая');
SET FOREIGN_KEY_CHECKS = 1;