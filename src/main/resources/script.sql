CREATE DATABASE IF NOT EXISTS RentalSystemDB;
USE RentalSystemDB;

CREATE TABLE Equipment
(
    equipmentId       VARCHAR(50)                                                                        NOT NULL,
    name              TINYTEXT                                                                           NOT NULL,
    category          VARCHAR(150),
    description       MEDIUMTEXT                                                                         NOT NULL,
    image             MEDIUMBLOB,
    `condition`       ENUM ('EXCELLENT', 'GOOD', 'NEEDS_REPAIR', 'OUT_OF_SERVICE') DEFAULT ('EXCELLENT') NOT NULL,
    rentalStatus      ENUM ('AVAILABLE', 'RENTED')                                 DEFAULT ('AVAILABLE') NOT NULL,
    type              VARCHAR(150),
    price             DOUBLE(30, 2)                                                DEFAULT (0)           NOT NULL,
    rentedPer         ENUM ('HOUR', 'DAY', 'WEEK', 'MONTH')                        DEFAULT ('DAY')       NOT NULL,
    nextAvailableDate DATETIME,
    PRIMARY KEY (equipmentId)
);

CREATE TABLE Sound
(
    equipmentId    VARCHAR(50)   NOT NULL,
    wattage        DOUBLE(10, 2) NOT NULL,
    inputVoltage   VARCHAR(20)   NOT NULL,
    amplifierClass VARCHAR(10),
    peakDecibel    DOUBLE(10, 2)
#     FOREIGN KEY (`equipmentId`) REFERENCES Equipment (equipmentId)
);

CREATE TABLE Stage
(
    equipmentId VARCHAR(50)   NOT NULL,
    length      DOUBLE(10, 2) NOT NULL,
    width       DOUBLE(10, 2) NOT NULL,
    height      DOUBLE(10, 2) NOT NULL
#     FOREIGN KEY (equipmentId) REFERENCES Equipment (equipmentId)
);

CREATE TABLE Light
(
    equipmentId VARCHAR(50)   NOT NULL,
    luminosity  INT(10)       NOT NULL,
    wattage     DOUBLE(10, 2) NOT NULL,
    voltage     VARCHAR(20)   NOT NULL
#     FOREIGN KEY (equipmentId) REFERENCES Equipment (equipmentId)
);

CREATE TABLE Power
(
    equipmentId   VARCHAR(50)                           NOT NULL,
    outputPower   DOUBLE(10, 2)                         NOT NULL,
    outputVoltage VARCHAR(20)                           NOT NULL,
    phase         INT(10)                               NOT NULL,
    fuelSource    ENUM ('GASOLINE', 'PROPANE','DIESEL') NOT NULL
#     FOREIGN KEY (equipmentId) REFERENCES Equipment (equipmentId)
);

CREATE TABLE User
(
    userId      VARCHAR(50) NOT NULL,
    firstName   VARCHAR(50) NOT NULL,
    lastName    VARCHAR(50) NOT NULL,
    email       VARCHAR(50),
    password    VARCHAR(24),
    accountType ENUM ('CUSTOMER', 'EMPLOYEE'),
    PRIMARY KEY (userId),
    UNIQUE (email)
);

CREATE TABLE Customer
(
    userId      VARCHAR(50) NOT NULL,
    address     TINYTEXT    NOT NULL,
    phoneNumber VARCHAR(10) NOT NULL
#     FOREIGN KEY (userId) REFERENCES User (userId)
);

CREATE TABLE Employee
(
    userId VARCHAR(50) NOT NULL
#     staffId VARCHAR(50) NOT NULL
#     FOREIGN KEY (userId) REFERENCES User (userId)
);

CREATE TABLE Invoice
(
    invoiceId       INT(200) AUTO_INCREMENT                       NOT NULL,
    customerId      VARCHAR(50),
    invoiceDate     DATETIME                                      NOT NULL,
    discount        DOUBLE(30, 2)              DEFAULT (0)        NOT NULL,
    transportCost   DOUBLE(30, 2)              DEFAULT (0)        NOT NULL,
    totalPrice      DOUBLE(50, 2)              DEFAULT (0)        NOT NULL,
    deliveryAddress VARCHAR(300),
    deliveryOption  ENUM ('DELIVER', 'PICKUP') DEFAULT ('PICKUP') NOT NULL,
#     FOREIGN KEY (customerId) REFERENCES Customer (customerId),
    PRIMARY KEY (invoiceId)
);

CREATE TABLE InvoiceItem
(
    invoiceItemId   INT(200) AUTO_INCREMENT       NOT NULL,
    invoiceId       INT(200)                      NOT NULL,
    equipmentId     VARCHAR(50)                   NOT NULL,
    quantity        INT(100)      DEFAULT (1)     NOT NULL,
    rentalStartDate DATETIME                      NOT NULL,
    rentalEndDate   DATETIME                      NOT NULL,
    totalPrice      DOUBLE(30, 2) DEFAULT (0)     NOT NULL,
    approved        BOOLEAN       DEFAULT (FALSE) NOT NULL,
    PRIMARY KEY (invoiceItemId)
#     FOREIGN KEY (invoiceId) REFERENCES Invoice (invoiceId),
#     FOREIGN KEY (equipmentId) REFERENCES Equipment (equipmentId)
);

CREATE TABLE Chat
(
    chatId VARCHAR(50) NOT NULL,
    PRIMARY KEY (chatId)
);

CREATE TABLE Message
(
    messageId  VARCHAR(50) NOT NULL,
    chatId     VARCHAR(50) NOT NULL,
    message    LONGTEXT,
    senderId   VARCHAR(50) NOT NULL,
    receiverId VARCHAR(50),
    sentDate   DATETIME,
    PRIMARY KEY (messageId)
#     FOREIGN KEY (chatId) REFERENCES Chat (chatId)
);

CREATE TABLE RentalRequest
(
    requestId    INT(200)                NOT NULL AUTO_INCREMENT,
    invoiceId    INT(200)                NOT NULL,
    requestDate  DATETIME                NOT NULL,
    customerId   VARCHAR(50),
    approved     BOOLEAN DEFAULT (FALSE) NOT NULL,
    approvedBy   VARCHAR(50),
    approvedDate DATETIME,
#     FOREIGN KEY (invoiceId) REFERENCES Invoice (invoiceId),
    PRIMARY KEY (requestId)
);

create table Transaction
(
    transactionId   INT(200)                  NOT NULL AUTO_INCREMENT,
    balance         DOUBLE(30, 2) DEFAULT (0) NOT NULL,
    paid            DOUBLE(30, 2) DEFAULT (0) NOT NULL,
    transactionDate DATETIME,
    requestId       INT(200)                  NOT NULL,
    PRIMARY KEY (transactionId)
);

ALTER TABLE User
    ADD CONSTRAINT user_email UNIQUE (email);

ALTER TABLE Customer
    ADD CONSTRAINT customer_user
        FOREIGN KEY (userId)
            REFERENCES User (userId);

ALTER TABLE Employee
    ADD CONSTRAINT employee_user
        FOREIGN KEY (userId)
            REFERENCES User (userId);

ALTER TABLE Invoice
    ADD CONSTRAINT Invoice_User
        FOREIGN KEY (customerId)
            REFERENCES Customer (userId);

ALTER TABLE InvoiceItem
    ADD CONSTRAINT InvoiceItem_Equipment
        FOREIGN KEY (equipmentId)
            REFERENCES Equipment (equipmentId);

ALTER TABLE Light
    ADD CONSTRAINT Light_Equipment
        FOREIGN KEY (equipmentId)
            REFERENCES Equipment (equipmentId);

ALTER TABLE Sound
    ADD CONSTRAINT Sound_Equipment
        FOREIGN KEY (equipmentId)
            REFERENCES Equipment (equipmentId);

ALTER TABLE Stage
    ADD CONSTRAINT Stage_Equipment
        FOREIGN KEY (equipmentId)
            REFERENCES Equipment (equipmentId);

ALTER TABLE InvoiceItem
    ADD CONSTRAINT InvoiceItem_Invoice
        FOREIGN KEY (invoiceId)
            REFERENCES Invoice (invoiceId);

ALTER TABLE Message
    ADD CONSTRAINT Message_Chat
        FOREIGN KEY (chatId)
            REFERENCES Chat (chatId);

ALTER TABLE Message
    ADD CONSTRAINT Message_User
        FOREIGN KEY (senderId)
            REFERENCES User (userId);

ALTER TABLE Power
    ADD CONSTRAINT Power_Equipment
        FOREIGN KEY (equipmentId)
            REFERENCES Equipment (equipmentId);

ALTER TABLE RentalRequest
    ADD CONSTRAINT RentalRequest_Employee
        FOREIGN KEY (approvedBy)
            REFERENCES Employee (userId);

ALTER TABLE RentalRequest
    ADD CONSTRAINT RentalRequest_Invoice
        FOREIGN KEY (invoiceId)
            REFERENCES Invoice (invoiceId);

ALTER TABLE RentalRequest
    ADD CONSTRAINT RentalRequest_Customer
        FOREIGN KEY (customerId)
            REFERENCES Customer (userId);

ALTER TABLE Transaction
    ADD CONSTRAINT Transaction_RentalRequest
        FOREIGN KEY (requestId)
            REFERENCES RentalRequest (requestId);
