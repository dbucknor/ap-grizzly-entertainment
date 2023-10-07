CREATE TABLE `Equipment`
(
    `equipmentId`       varchar(50),
    `description`       text(350),
    `condition`         varchar(50),
    `name`              varchar(80),
    `category`          varchar(50),
    `rentalStatus`      varchar(16),
    `type`              varchar(24),
    `price`             double(16, 2),
    `rentBy`            varchar(16),
    `nextAvailableDate` varchar(50),
    PRIMARY KEY (`equipmentId`)
);

CREATE TABLE `Customer`
(
    `customerId`  varchar(50),
    `firstname`   varchar(50),
    `lastName`    varchar(50),
    `address`     varchar(80),
    `phoneNumber` varchar(10),
    `password`    varchar(16),
    `imageUrl`    varchar(150),
    PRIMARY KEY (`customerId`)
);

CREATE TABLE `Employee`
(
    `staffId`     varchar(50),
    `firstname`   varchar(50),
    `lastName`    varchar(50),
    `address`     varchar(80),
    `phoneNumber` varchar(10),
    `imageUrl`    varchar(150),
    `password`    varchar(16),
    PRIMARY KEY (`staffId`)
);

CREATE TABLE `Invoices`
(
    `invoiceId`   INT AUTO_INCREMENT PRIMARY KEY,
    `customerId`  INT,
    `invoiceDate` DATE,
    `totalPrice`  DECIMAL(10, 2)
);

CREATE TABLE InvoiceDetails
(
    invoiceDetailId INT AUTO_INCREMENT PRIMARY KEY,
    invoiceId       INT,
    equipmentId     INT,
    Quantity        INT,
    RentalStartDate DATE,
    RentalEndDate   DATE,

    FOREIGN KEY (invoiceId) REFERENCES Invoices (invoiceId),
    FOREIGN KEY (equipmentId) REFERENCES Equipment (equipmentId)
);
