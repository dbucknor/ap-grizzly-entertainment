CREATE DATABASE IF NOT EXISTS RentalSystemDB;
USE RentalSystemDB;

create table Chat
(
    chatId varchar(255) not null,
    primary key (chatId)
) engine = InnoDB;

create table Customer
(
    address     varchar(255),
    customerId  varchar(255),
    phoneNumber varchar(255),
    userId      varchar(255) not null,
    primary key (userId)
) engine = InnoDB;

create table Employee
(
    staffId varchar(255),
    userId  varchar(255) not null,
    primary key (userId)
) engine = InnoDB;

create table Equipment
(
    equipmentId       varchar(255) not null,
    category          varchar(255),
    `condition`       varchar(255),
    description       varchar(255),
    name              varchar(255),
    nextAvailableDate datetime(6),
    price             double precision,
    rentalStatus      varchar(255),
    rentedPer         varchar(255),
    type              varchar(255),
    primary key (equipmentId)
) engine = InnoDB;

create table Invoice
(
    invoiceId       integer not null auto_increment,
    deliveryAddress varchar(255),
    deliveryOption  varchar(255),
    discount        double precision,
    invoiceDate     datetime(6),
    totalPrice      double precision,
    customerId      varchar(255),
    primary key (invoiceId)
) engine = InnoDB;

create table InvoiceItem
(
    invoiceItemId   integer not null auto_increment,
    approved        bit,
    quantity        integer,
    rentalEndDate   datetime(6),
    rentalStartDate datetime(6),
    totalPrice      double precision,
    equipmentId     varchar(255),
    invoiceId       integer,
    primary key (invoiceItemId)
) engine = InnoDB;

create table Light
(
    luminosity  integer,
    voltage     varchar(255),
    wattage     double precision,
    equipmentId varchar(255) not null,
    primary key (equipmentId)
) engine = InnoDB;

create table Messages
(
    messageId integer not null auto_increment,
    message   varchar(255),
    sentDate  datetime(6),
    chatId    varchar(255),
    senderId  varchar(255),
    primary key (messageId)
) engine = InnoDB;

create table Power
(
    fuelSource    integer,
    outputPower   double precision,
    outputVoltage varchar(255),
    phase         integer,
    equipmentId   varchar(255) not null,
    primary key (equipmentId)
) engine = InnoDB;

create table rentalrequest
(
    requestId    integer not null,
    approved     bit,
    approvedDate datetime(6),
    requestDate  datetime(6),
    approvedBy   varchar(255),
    invoiceId    integer,
    customerId   varchar(255),
    primary key (requestId)
) engine = InnoDB;

create table Sound
(
    amplifierClass varchar(255),
    inputVoltage   varchar(255),
    peakDecibel    double precision,
    wattage        double precision,
    equipmentId    varchar(255) not null,
    primary key (equipmentId)
) engine = InnoDB;

create table Stage
(
    height      double precision,
    length      double precision,
    width       double precision,
    equipmentId varchar(255) not null,
    primary key (equipmentId)
) engine = InnoDB;

create table Transaction
(
    transactionId   integer not null auto_increment,
    balance         double precision,
    paid            double precision,
    transactionDate datetime(6),
    requestId       integer,
    primary key (transactionId)
) engine = InnoDB;

create table User
(
    userId      varchar(255) not null,
    accountType varchar(255),
    email       varchar(255),
    firstName   varchar(255),
    lastName    varchar(255),
    password    varchar(255),
    primary key (userId)
) engine = InnoDB;

alter table User
    add constraint UK_e6gkqunxajvyxl5uctpl2vl2p unique (email);

alter table Customer
    add constraint FKt2cq0hxkam24xq25hk35qnq3k
        foreign key (userId)
            references User (userId);

alter table Employee
    add constraint FKbq1ldqg32tpfxv96pwj98wn51
        foreign key (userId)
            references User (userId);

alter table Invoice
    add constraint FK76vy5nmaorb33949pvcbeg6y3
        foreign key (customerId)
            references Customer (userId);

alter table InvoiceItem
    add constraint FKa8vs9jutadq03y2idrsv7xatl
        foreign key (equipmentId)
            references Equipment (equipmentId);

alter table InvoiceItem
    add constraint FK3h5rovfi2uydwe0ra4lpfaw0i
        foreign key (invoiceId)
            references Invoice (invoiceId);

alter table Light
    add constraint FK1xqm683m5u5j83jpv3i4xwbbk
        foreign key (equipmentId)
            references Equipment (equipmentId);

alter table Messages
    add constraint FKtqrij8x0wog32ttwkwq673w1u
        foreign key (chatId)
            references Chat (chatId);

alter table Messages
    add constraint FK15lv01oerqrromtdjmjcfcfuw
        foreign key (senderId)
            references User (userId);

alter table Power
    add constraint FKpv4dn2jl8fm5mi9k4vir5k9iw
        foreign key (equipmentId)
            references Equipment (equipmentId);

alter table rentalrequest
    add constraint FK2tsogjt7593uio1kp8nb8rik9
        foreign key (approvedBy)
            references Employee (userId);

alter table rentalrequest
    add constraint FKajj4od5kdiekca96dbvrv66tf
        foreign key (invoiceId)
            references Invoice (invoiceId);

alter table rentalrequest
    add constraint FK84ru9klfemk3dx8bfwunfh1pb
        foreign key (customerId)
            references Customer (userId);

alter table Sound
    add constraint FKf38odunvl192qpiwkuu9ge70c
        foreign key (equipmentId)
            references Equipment (equipmentId);

alter table Stage
    add constraint FKtrptxv572qb1fg0ad2bul4y5m
        foreign key (equipmentId)
            references Equipment (equipmentId);

alter table Transaction
    add constraint FKetkmdl1bagufbvuuilb7all7h
        foreign key (requestId)
            references rentalrequest (requestId);
