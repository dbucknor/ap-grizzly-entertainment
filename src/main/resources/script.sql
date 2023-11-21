CREATE DATABASE IF NOT EXISTS RentalSystemDB;
USE RentalSystemDB;

CREATE TABLE Equipment
(
    equipmentId       VARCHAR(50)                                                  NOT NULL,
    name              TINYTEXT                                                     NOT NULL,
    category          VARCHAR(50),
    description       MEDIUMTEXT                                                   NOT NULL,
    image             MEDIUMBLOB,
    `condition`       ENUM ('EXCELLENT', 'GOOD', 'NEEDS_REPAIR', 'OUT_OF_SERVICE') NOT NULL,
    rentalStatus      ENUM ('AVAILABLE', 'RENTED')                                 NOT NULL,
    type              VARCHAR(150),
    price             DOUBLE(30, 2)                                                NOT NULL,
    rentedPer         ENUM ('HOUR', 'DAY', 'WEEK', 'MONTH')                        NOT NULL,
    nextAvailableDate DATETIME,
    PRIMARY KEY (equipmentId)
);

CREATE TABLE Sound
(
    equipmentId    VARCHAR(50)   NOT NULL,
    wattage        DOUBLE(10, 2) NOT NULL,
    inputVoltage   VARCHAR(20)   NOT NULL,
    amplifierClass VARCHAR(10),
    peakDecibel    DOUBLE(10, 2),
    FOREIGN KEY (`equipmentId`) REFERENCES Equipment (equipmentId)
);

CREATE TABLE Stage
(
    equipmentId VARCHAR(50)   NOT NULL,
    length      DOUBLE(10, 2) NOT NULL,
    width       DOUBLE(10, 2) NOT NULL,
    height      DOUBLE(10, 2) NOT NULL,
    FOREIGN KEY (equipmentId) REFERENCES Equipment (equipmentId)
);

CREATE TABLE Light
(
    equipmentId VARCHAR(50)   NOT NULL,
    luminosity  INT(10)       NOT NULL,
    wattage     DOUBLE(10, 2) NOT NULL,
    voltage     VARCHAR(20)   NOT NULL,
    FOREIGN KEY (equipmentId) REFERENCES Equipment (equipmentId)
);

CREATE TABLE Power
(
    equipmentId   VARCHAR(50)   NOT NULL,
    outputPower   DOUBLE(10, 2) NOT NULL,
    outputVoltage VARCHAR(20)   NOT NULL,
    phase         INT(10)       NOT NULL,
    fuelSource    VARCHAR(100)  NOT NULL,
    FOREIGN KEY (equipmentId) REFERENCES Equipment (equipmentId)
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
    customerId  VARCHAR(50) NOT NULL,
    userId      VARCHAR(50) NOT NULL,
    address     TINYTEXT    NOT NULL,
    phoneNumber VARCHAR(10) NOT NULL,
    FOREIGN KEY (userId) REFERENCES User (userId)
);

CREATE TABLE Employee
(
    userId  VARCHAR(50) NOT NULL,
    staffId VARCHAR(50) NOT NULL,
    FOREIGN KEY (userId) REFERENCES User (userId)
);

CREATE TABLE Invoice
(
    invoiceId       INT(200) AUTO_INCREMENT    NOT NULL,
    customerId      VARCHAR(50),
    invoiceDate     DATETIME                   NOT NULL,
    discount        DOUBLE(30, 2)              NOT NULL,
    transportCost   DOUBLE(30, 2)              NOT NULL,
    totalPrice      DOUBLE(50, 2)              NOT NULL,
    deliveryAddress VARCHAR(300),
    deliveryOption  ENUM ('DELIVER', 'PICKUP') NOT NULL,
#     FOREIGN KEY (customerId) REFERENCES Customer (customerId),
    PRIMARY KEY (invoiceId)
);

CREATE TABLE InvoiceItem
(
    invoiceItemId   INT(200) AUTO_INCREMENT NOT NULL,
    invoiceId       INT(200)                NOT NULL,
    equipmentId     VARCHAR(50)             NOT NULL,
    quantity        INT(100)                NOT NULL,
    rentalStartDate DATETIME                NOT NULL,
    rentalEndDate   DATETIME                NOT NULL,
    totalPrice      DOUBLE(30, 2)           NOT NULL,
    approved        BOOLEAN                 NOT NULL,
    PRIMARY KEY (invoiceItemId),
    FOREIGN KEY (invoiceId) REFERENCES Invoice (invoiceId),
    FOREIGN KEY (equipmentId) REFERENCES Equipment (equipmentId)
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
    PRIMARY KEY (messageId),
    FOREIGN KEY (chatId) REFERENCES Chat (chatId)
);

CREATE TABLE RentalRequest
(
    requestId    INT(200) NOT NULL,
    invoiceId    INT(200) NOT NULL,
    requestDate  DATETIME NOT NULL,
    customerId   VARCHAR(50),
    approved     BOOLEAN  NOT NULL,
    approvedBy   VARCHAR(50),
    approvedDate DATETIME,
    FOREIGN KEY (invoiceId) REFERENCES Invoice (invoiceId),
#     FOREIGN KEY (customerId) REFERENCES Customer (customerId),
    PRIMARY KEY (requestId)
);

# CREATE TABLE MaintenanceLog
# (
#     timestamp   DOUBLE,
#     date        DATETIME,
#     equipmentId VARCHAR(50),
#     `condition` VARCHAR(16),
#     details     VARCHAR(350),
#     PRIMARY KEY (timestamp)
# );