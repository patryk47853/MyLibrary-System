CREATE USER 'patryk47853'@'localhost' IDENTIFIED BY 'patryk47853';

GRANT ALL PRIVILEGES ON  *.* TO 'patryk47853'@'localhost';

CREATE DATABASE  IF NOT EXISTS `mylibrary_system`;

USE `mylibrary_system`;


DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
	`id` int(6) NOT NULL AUTO_INCREMENT,
    `username` varchar(64) NOT NULL,
    `password` varchar(64) NOT NULL,
    `email` varchar(64) NOT NULL,
    `active` boolean DEFAULT FALSE,
    `first_name` varchar(64) DEFAULT NULL,
    `last_name` varchar(64) DEFAULT NULL,
    `phone_number` varchar(64) DEFAULT NULL,
    `address` varchar(255) DEFAULT NULL,
    `postal_code` varchar(64) DEFAULT NULL,
    `city` varchar(64) DEFAULT NULL,
    `created_at` timestamp,

	PRIMARY KEY (`id`)
)