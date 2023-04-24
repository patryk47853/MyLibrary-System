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
);

DROP TABLE IF EXISTS `library_cards`;

CREATE TABLE `library_cards` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(80) DEFAULT NULL,
  `last_name` varchar(80) DEFAULT NULL,
  `phone_number` varchar(80) DEFAULT NULL,
  `address` varchar(120) DEFAULT NULL,
  `postal` varchar(80) DEFAULT NULL,
  `city` varchar(80) DEFAULT NULL,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `user_id` INT UNIQUE,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
    PRIMARY KEY (`id`)
  
  ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `books` (
                        `id` int(6) NOT NULL AUTO_INCREMENT,
                        `title` varchar(255) NOT NULL,
                        `page_count` int(6) NOT NULL,
                        `rating` decimal(3, 2) NOT NULL,
                        `description` text NOT NULL,
                        `image_link` varchar(255) NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `authors` (
                          `id` int(6) NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) NOT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `book_authors` (
                               `book_id` int(6) NOT NULL,
                               `author_id` int(6) NOT NULL,
                               PRIMARY KEY (`book_id`, `author_id`),
                               KEY `book` (`book_id`),
                               CONSTRAINT `FK_BOOK_AUTHOR_BOOK` FOREIGN KEY (`book_id`)
                                   REFERENCES `book` (`id`)
                                   ON DELETE CASCADE ON UPDATE NO ACTION,
                               KEY `author` (`author_id`),
                               CONSTRAINT `FK_BOOK_AUTHOR_AUTHOR` FOREIGN KEY (`author_id`)
                                   REFERENCES `author` (`id`)
                                   ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE `bookcategories` (
                                `id` int(6) NOT NULL AUTO_INCREMENT,
                                `name` varchar(255) NOT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `book_bookcategories` (
                                     `book_id` int(6) NOT NULL,
                                     `bookcategory_id` int(6) NOT NULL,
                                     PRIMARY KEY (`book_id`, `bookcategory_id`),
                                     KEY `book` (`book_id`),
                                     CONSTRAINT `FK_BOOK_BOOKCATEGORY_BOOK` FOREIGN KEY (`book_id`)
                                         REFERENCES `book` (`id`)
                                         ON DELETE CASCADE ON UPDATE NO ACTION,
                                     KEY `bookcategory` (`bookcategory_id`),
                                     CONSTRAINT `FK_BOOK_BOOKCATEGORY_BOOKCATEGORY` FOREIGN KEY (`bookcategory_id`)
                                         REFERENCES `bookcategory` (`id`)
                                         ON DELETE CASCADE ON UPDATE NO ACTION
);

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `roles` (`name`) VALUES
                                 ("USER"), ("READER"), ("LIBRARIAN"), ("ADMIN");