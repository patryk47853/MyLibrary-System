CREATE DATABASE  IF NOT EXISTS `mylibrary_system`;

USE `mylibrary_system`;

CREATE TABLE `roles` (
                         `id` int(6) NOT NULL AUTO_INCREMENT,
                         `name` varchar(45) NOT NULL UNIQUE,

                         PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
                         `id` int(6) NOT NULL AUTO_INCREMENT,
                         `username` varchar(100) NOT NULL UNIQUE,
                         `email` varchar(255) NOT NULL UNIQUE,
                         `password` varchar(255) NOT NULL,
                         `enabled` boolean NOT NULL,
                         `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,

                         PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users_roles` (
                               `users_id` int(6) NOT NULL,
                               `roles_id` int(6) NOT NULL,

                               PRIMARY KEY (`users_id`, `roles_id`),

                               KEY `users` (`users_id`),
                               CONSTRAINT `FK_USERS_AUTH` FOREIGN KEY (`users_id`)
                                   REFERENCES `users` (`id`)
                                   ON DELETE NO ACTION ON UPDATE NO ACTION,

                               KEY `roles` (`roles_id`),
                               CONSTRAINT `FK_USERS_ROLES` FOREIGN KEY (`roles_id`)
                                   REFERENCES `roles` (`id`)
                                   ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `roles` (`name`) VALUES
                                 ("USER"), ("LIBRARIAN"), ("ADMIN");