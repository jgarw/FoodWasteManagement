CREATE DATABASE FoodWasteReduction;

USE FoodWasteReduction;

CREATE TABLE USERS(
user_id int AUTO_INCREMENT PRIMARY KEY,
name varchar(100) NOT NULL,
email varchar(100) NOT NULL UNIQUE,
password varchar(100) NOT NULL,
user_type varchar(50) NOT NULL,
phone varchar(50)
);

SELECT * FROM USERS;

CREATE TABLE CONSUMERS(
consumer_id int AUTO_INCREMENT PRIMARY KEY,
user_id int NOT NULL,
FOREIGN KEY (user_id) REFERENCES USERS(user_id)
);

CREATE TABLE RETAILERS(
retailer_id int AUTO_INCREMENT PRIMARY KEY,
user_id int NOT NULL,
FOREIGN KEY (user_id) REFERENCES USERS(user_id)
);

CREATE TABLE ORGANIZATIONS(
org_id int AUTO_INCREMENT PRIMARY KEY,
user_id int NOT NULL,
FOREIGN KEY (user_id) REFERENCES USERS(user_id)
);


CREATE TABLE FoodItems (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    retailer_id INT,
    item_name VARCHAR(255),
    expiration_date DATE,
    quantity int, 
    price DECIMAL(10, 2),
    surplus BOOLEAN,
    listing_type VARCHAR(255),
    CONSTRAINT fk_retailer FOREIGN KEY (retailer_id) REFERENCES RETAILERS(retailer_id)
);

CREATE TABLE Purchases (
    purchase_id INT AUTO_INCREMENT PRIMARY KEY,
    item_id INT,
    consumer_id INT,
    quantity INT,
    FOREIGN KEY (item_id) REFERENCES fooditems(item_id),
    FOREIGN KEY (consumer_id) REFERENCES consumers(consumer_id)
);

CREATE TABLE claims (
    claim_id INT AUTO_INCREMENT PRIMARY KEY,
    item_id INT,
    org_id INT,
    quantity INT,
    FOREIGN KEY (item_id) REFERENCES fooditems(item_id),
    FOREIGN KEY (org_id) REFERENCES organizations(org_id)
);

CREATE TABLE Subscribers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL 

);