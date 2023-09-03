CREATE DATABASE IF NOT EXISTS `mylibrary_system`;

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

CREATE TABLE authors (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255)
);

CREATE TABLE categories (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255)
);

CREATE TABLE books (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255),
                       publishedDate VARCHAR(50),
                       pageCount VARCHAR(20),
                       selfLink VARCHAR(255),
                       description TEXT,
                       author_id INT,
                       category_id INT,
                       FOREIGN KEY (author_id) REFERENCES authors(id),
                       FOREIGN KEY (category_id) REFERENCES categories(id)
);

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

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `roles` (`name`) VALUES
                                 ("USER"), ("READER"), ("LIBRARIAN"), ("ADMIN");