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

DROP TABLE IF EXISTS `library_card`;

CREATE TABLE `library_card` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(80) DEFAULT NULL,
  `last_name` varchar(80) DEFAULT NULL,
  `phone_number` varchar(80) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `address` varchar(120) DEFAULT NULL,
  `postal` varchar(80) DEFAULT NULL,
  `city` varchar(80) DEFAULT NULL,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USERS_CARDS` (`user_id`),
  CONSTRAINT `FK_LIBRARY_CARD` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `roles` (`name`) VALUES
                                 ("USER"), ("LIBRARIAN"), ("ADMIN");