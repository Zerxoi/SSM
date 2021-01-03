drop database if exists mybatis;

create database mybatis;

use mybatis;

CREATE TABLE `t_author` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(32),
    `password` VARCHAR(32),
    `email` VARCHAR(32),
    `interests` VARCHAR(32)
)  ENGINE=INNODB CHARACTER SET=UTF8MB4;
    
CREATE TABLE `t_post` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `author_id` INT,
    `created_on` DATETIME,
    `body` VARCHAR(1024),
    FOREIGN KEY (`author_id`)
        REFERENCES `t_author` (`id`)
)  ENGINE=INNODB CHARACTER SET=UTF8MB4;
    
CREATE TABLE `t_comment` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `author_id` INT,
    `post_id` INT,
    `text` VARCHAR(512),
    FOREIGN KEY (`post_id`)
        REFERENCES `t_post` (`id`),
    FOREIGN KEY (`author_id`)
        REFERENCES `t_author` (`id`)
)  ENGINE=INNODB CHARACTER SET=UTF8MB4;

INSERT INTO `t_author` (`id`, `username`, `password`, `email`, `interests`) VALUES ('1', 'zerxoi', '123456', 'zerxoi@163.com', 'java,js');
INSERT INTO `t_author` (`id`, `username`, `password`, `email`, `interests`) VALUES ('2', 'kaguya', '654321', 'kaguya@gmail.com', 'cpp,python,golang');

INSERT INTO `t_post` (`id`, `author_id`, `created_on`, `body`) VALUES ('1', '1', '2020-12-15 22:23:12', 'Best Anime');
INSERT INTO `t_post` (`id`, `author_id`, `created_on`, `body`) VALUES ('2', '1', '2020-12-17 10:58:24', 'Fuck My Life!');

INSERT INTO `t_comment` (`id`, `author_id`, `post_id`, `text`) VALUES ('1', '1', '1', 'Attack on Titan');
INSERT INTO `t_comment` (`id`, `author_id`, `post_id`, `text`) VALUES ('2', '2', '1', 'YYDS?');
INSERT INTO `t_comment` (`id`, `author_id`, `post_id`, `text`) VALUES ('3', '1', '1', 'Yes');
INSERT INTO `t_comment` (`id`, `author_id`, `post_id`, `text`) VALUES ('4', '1', '2', 'FML');
INSERT INTO `t_comment` (`id`, `author_id`, `post_id`, `text`) VALUES ('5', '2', '2', '发霉啦');

delimiter //

create procedure selectPostAuthorById(in id_param int)
begin
	select * from t_post where id = id_param;
    select * from t_author where id = id_param;
end//

-- drop procedure selectPostAuthorById//

create procedure selectPostAuthorCommentsById(in id_param int)
begin
	select * from t_post where id = id_param;
    select * from t_author where id = id_param;
	select * from t_comment where post_id = id_param;
end//

-- drop procedure selectPostAuthorCommentsById//
