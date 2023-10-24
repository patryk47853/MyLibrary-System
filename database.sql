CREATE DATABASE mylibrary_system;
USE mylibrary_system;

CREATE TABLE roles
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE users
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    username   VARCHAR(100) NOT NULL UNIQUE,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    enabled    BOOLEAN      NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE library_cards
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    first_name   VARCHAR(80),
    last_name    VARCHAR(80),
    phone_number VARCHAR(80),
    address      VARCHAR(120),
    postal       VARCHAR(80),
    city         VARCHAR(80),
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id      INT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE users_roles
(
    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE categories
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE authors
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE books
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    title           VARCHAR(255) NOT NULL,
    cover_image_url VARCHAR(400),
    publisher       VARCHAR(255),
    published_date  VARCHAR(255),
    page_count      VARCHAR(20),
    google_books_id VARCHAR(255) NOT NULL,
    average_rating  DECIMAL(3, 2),
    self_link       VARCHAR(255),
    description     TEXT,
    category_id     INT,
    author_id       INT,
    FOREIGN KEY (category_id) REFERENCES categories (id),
    FOREIGN KEY (author_id) REFERENCES authors (id)
);

CREATE TABLE book_authors
(
    book_id   INT,
    author_id INT,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books (id),
    FOREIGN KEY (author_id) REFERENCES authors (id)
);

CREATE TABLE book_categories
(
    book_id    INT,
    category_id INT,
    PRIMARY KEY (book_id, category_id),
    FOREIGN KEY (book_id) REFERENCES books (id),
    FOREIGN KEY (category_id) REFERENCES categories (id)
);


INSERT INTO roles (name)
VALUES ('USER'),
       ('READER'),
       ('LIBRARIAN'),
       ('ADMIN');