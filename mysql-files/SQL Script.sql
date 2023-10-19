CREATE DATABASE IF NOT EXISTS RentalSystemDB;
USE RentalSystemDB;

CREATE TABLE IF NOT EXISTS Equipment
(
    `equipmentId`       VARCHAR(50),
    `description`       text(350),
    `condition`         VARCHAR(50),
    `name`              VARCHAR(80),
    `category`          VARCHAR(50),
    `rentalStatus`      VARCHAR(16),
    `type`              VARCHAR(24),
    `price`             DOUBLE(16, 2),
    `rentBy`            VARCHAR(16),
    `nextAvailableDate` VARCHAR(50),
    PRIMARY KEY (`equipmentId`)
);

CREATE TABLE IF NOT EXISTS Customer
(
    `customerId`  VARCHAR(50),
    `firstname`   VARCHAR(50),
    `lastName`    VARCHAR(50),
    `address`     VARCHAR(80),
    `phoneNumber` VARCHAR(10),
    `password`    VARCHAR(16),
    `imageUrl`    MEDIUMBLOB,
    PRIMARY KEY (`customerId`)
);

CREATE TABLE IF NOT EXISTS Employee
(
    `staffId`     VARCHAR(50),
    `firstname`   VARCHAR(50),
    `lastName`    VARCHAR(50),
    `address`     VARCHAR(80),
    `phoneNumber` VARCHAR(10),
    `imageUrl`    VARCHAR(150),
    `password`    VARCHAR(16),
    PRIMARY KEY (`staffId`)
);

CREATE TABLE IF NOT EXISTS Invoices
(
    `invoiceId`   INT AUTO_INCREMENT,
    `customerId`  INT,
    `invoiceDate` DATE,
    `totalPrice`  DECIMAL(10, 2),
    PRIMARY KEY (`invoiceId`)
);

CREATE TABLE IF NOT EXISTS InvoiceItem
(
    invoiceDetailId INT AUTO_INCREMENT,
    invoiceId       INT,
    equipmentId     VARCHAR(50),
    quantity        INT,
    rentalStartDate DATE,
    rentalEndDate   DATE,
    PRIMARY KEY (`invoiceDetailId`),
    FOREIGN KEY (invoiceId) REFERENCES Invoices (invoiceId),
    FOREIGN KEY (equipmentId) REFERENCES Equipment (equipmentId)
);

CREATE TABLE IF NOT EXISTS Messages
(
    messageID  VARCHAR(50),
    message    LONGTEXT,
    senderId   VARCHAR(50),
    date       DATETIME,
    receiverId VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS RentalRequests
(
    requestId   INT,
    requestDate DATETIME,
    customerId  VARCHAR(50),
    approved    BOOLEAN,
    approvedBy  VARCHAR(50),
    PRIMARY KEY (requestId),
    FOREIGN KEY (customerId) REFERENCES Customer (customerId),
    FOREIGN KEY (requestId) REFERENCES Invoices (invoiceId),
    FOREIGN KEY (approvedBy) REFERENCES Employee (staffId)
);

