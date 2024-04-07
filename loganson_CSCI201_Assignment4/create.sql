-- Create the database
DROP DATABASE IF EXISTS JoesTable;
CREATE DATABASE JoesTable;
USE JoesTable;

-- Create the Users table
CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL
);

ALTER TABLE Users AUTO_INCREMENT = 10;

-- Create the Restaurants table
CREATE TABLE IF NOT EXISTS Restaurants (
    restaurant_id Varchar(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(25),
    cuisine VARCHAR(50),
    price VARCHAR(11),
    rating VARCHAR(11),
    pictureUrl VARCHAR(255),
    websiteUrl VARCHAR(255)
);

-- Create the Favorites table
CREATE TABLE IF NOT EXISTS Favorites (
	favorite_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    restaurant_id Varchar(50) NOT NULL
);

-- Create the Reservations table
CREATE TABLE IF NOT EXISTS Reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    restaurant_id Varchar(50) NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL
);
