USE rentalsystemdb;

INSERT INTO Equipment (equipmentId, name, category, description, `health`, rentalStatus, type, price, rentedPer,
                       nextAvailableDate)
VALUES ('light1', 'LED Stage Light', 'Lighting', 'High-performance LED stage light suitable for various events.',
        'EXCELLENT', 'AVAILABLE', 'LED', 75.0, 'HOUR', '2023-01-01 09:00:00'),
       ('sound1', 'Wireless Microphone', 'Sound', 'Professional wireless microphone with clear sound quality.', 'GOOD',
        'RENTED', 'Wireless', 50.0, 'DAY', '2023-01-05 15:30:00'),
       ('power1', 'Portable Generator', 'Power', 'Compact and powerful generator for outdoor events.', 'EXCELLENT',
        'AVAILABLE', 'Portable', 120.0, 'WEEK', '2023-02-10 12:00:00'),
       ('stage1', 'Mobile Stage', 'Stage', 'Mobile stage setup with adjustable height and easy assembly.',
        'NEEDS_REPAIR', 'AVAILABLE', 'Mobile', 200.0, 'MONTH', '2023-03-20 14:45:00'),
       ('light2', 'Spotlight', 'Lighting', 'High-intensity spotlight for highlighting key elements on stage.', 'GOOD',
        'AVAILABLE', 'Spotlight', 90.0, 'HOUR', '2023-01-10 10:30:00'),
       ('sound2', 'PA System', 'Sound', 'Complete PA system with speakers, mixer, and microphones.', 'EXCELLENT',
        'RENTED', 'PA System', 150.0, 'DAY', '2023-01-08 13:15:00'),
       ('power2', 'Industrial Generator', 'Power',
        'Industrial-grade generator for large-scale events and construction sites.', 'OUT_OF_SERVICE', 'AVAILABLE',
        'Industrial', 300.0, 'WEEK', '2023-04-05 08:00:00'),
       ('stage2', 'Concert Stage', 'Stage', 'Spacious concert stage with advanced lighting and sound setup.',
        'EXCELLENT', 'RENTED', 'Concert', 500.0, 'MONTH', '2023-02-25 11:00:00'),
       ('light3', 'Flood Light', 'Lighting', 'Powerful floodlight for illuminating wide areas.', 'NEEDS_REPAIR',
        'AVAILABLE', 'Floodlight', 120.0, 'HOUR', '2023-01-15 16:45:00'),
       ('sound3', 'DJ Mixer', 'Sound', 'Professional DJ mixer with multiple channels and effects.', 'EXCELLENT',
        'AVAILABLE', 'DJ Equipment', 80.0, 'DAY', '2023-03-01 18:30:00'),
       ('power3', 'Backup Power Supply', 'Power', 'Reliable backup power supply for critical equipment during events.',
        'GOOD', 'AVAILABLE', 'Backup Power', 100.0, 'WEEK', '2023-04-15 09:30:00'),
       ('stage3', 'Theater Stage', 'Stage', 'Theater-style stage with curtains and proper acoustics.', 'OUT_OF_SERVICE',
        'AVAILABLE', 'Theater', 350.0, 'MONTH', '2023-03-10 14:00:00'),
       ('light4', 'Strobe Light', 'Lighting', 'Dynamic strobe light for creating exciting visual effects.', 'EXCELLENT',
        'AVAILABLE', 'Strobe Light', 60.0, 'HOUR', '2023-01-20 20:00:00'),
       ('sound4', 'Subwoofer', 'Sound', 'Powerful subwoofer for enhancing low-frequency audio.', 'GOOD', 'AVAILABLE',
        'Subwoofer', 70.0, 'DAY', '2023-03-05 22:15:00'),
       ('power4', 'Silent Generator', 'Power',
        'Silent generator with minimal noise output for events with noise restrictions.', 'EXCELLENT', 'AVAILABLE',
        'Silent Generator', 200.0, 'WEEK', '2023-04-20 17:30:00'),
       ('stage4', 'Outdoor Stage', 'Stage', 'Outdoor stage with weather-resistant construction for open-air events.',
        'NEEDS_REPAIR', 'AVAILABLE', 'Outdoor', 450.0, 'MONTH', '2023-03-15 19:45:00');

-- Light
INSERT INTO Light (equipmentId, luminosity, wattage, voltage)
VALUES ('light1', 1000, 50.5, '120V'),
       ('light2', 800, 40.0, '110V'),
       ('light3', 1200, 60.0, '220V'),
       ('light4', 1500, 75.5, '240V');

-- Sound
INSERT INTO Sound (equipmentId, wattage, inputVoltage, amplifierClass, peakDecibel)
VALUES ('sound1', 500, '110V', 'Class A', 120.5),
       ('sound2', 800, '220V', 'Class B', 130.0),
       ('sound3', 1200, '110V', 'Class C', 140.0),
       ('sound4', 1000, '240V', 'Class D', 135.5);

-- Power
INSERT INTO Power (equipmentId, outputPower, outputVoltage, phase, fuelSource)
VALUES ('power1', 5000, '220V', 3, 'GASOLINE'),
       ('power2', 8000, '240V', 1, 'DIESEL'),
       ('power3', 10000, '110V', 3, 'PROPANE'),
       ('power4', 6000, '120V', 1, 'GASOLINE');

-- Stage
INSERT INTO Stage (equipmentId, length, width, height)
VALUES ('stage1', 10.0, 8.0, 1.0),
       ('stage2', 15.0, 12.0, 1.5),
       ('stage3', 20.0, 16.0, 2.0),
       ('stage4', 25.0, 20.0, 2.5);


INSERT INTO User (userId, firstName, lastName, email, password, accountType)
VALUES ('user1', 'John', 'Doe', 'john.doe@example.com', 'password1', 'CUSTOMER'),
       ('user2', 'Jane', 'Smith', 'jane.smith@example.com', 'password2', 'CUSTOMER'),
       ('user3', 'Alice', 'Johnson', 'alice.johnson@example.com', 'password3', 'CUSTOMER'),
       ('user4', 'Bob', 'Williams', 'bob.williams@example.com', 'password4', 'CUSTOMER'),
       ('user5', 'Eva', 'Brown', 'eva.brown@example.com', 'password5', 'CUSTOMER'),
       ('user6', 'Michael', 'Jones', 'michael.jones@example.com', 'password6', 'CUSTOMER'),
       ('user7', 'Sophie', 'Taylor', 'sophie.taylor@example.com', 'password7', 'CUSTOMER'),
       ('user8', 'David', 'Lee', 'david.lee@example.com', 'password8', 'CUSTOMER'),
       ('user9', 'Emily', 'Clark', 'emily.clark@example.com', 'password9', 'EMPLOYEE'),
       ('user10', 'Daniel', 'Baker', 'daniel.baker@example.com', 'password10', 'EMPLOYEE');

INSERT INTO Customer (userId, address, phoneNumber)
VALUES ('user1', '123 Main St, Cityville', '1234567890'),
       ('user2', '456 Oak St, Townsville', '9876543210'),
       ('user3', '789 Pine St, Villagetown', '5551234567'),
       ('user4', '101 Elm St, Hamletville', '4449876543'),
       ('user5', '202 Cedar St, Boroughburg', '6789012345'),
       ('user6', '303 Maple St, Township', '3212345678'),
       ('user7', '404 Birch St, Settlementville', '8765432109'),
       ('user8', '505 Spruce St, Metropolis', '2345678901');

INSERT INTO Employee (userId)
VALUES ('user9'),
       ('user10');


INSERT INTO Invoice (customerId, invoiceDate, discount, transportCost, totalPrice, deliveryAddress, deliveryOption)
VALUES ('user1', '2023-01-02 12:45:00', 10.0, 20.0, 500.0, '123 Main St, Cityville', 'DELIVER'),
       ('user3', '2023-03-02 10:20:00', 5.0, 15.0, 700.0, '789 Pine St, Villagetown', 'PICKUP'),
       ('user3', '2023-03-02 10:20:00', 5.0, 15.0, 700.0, '789 Pine St, Villagetown', 'PICKUP'),
       ('user4', '2023-04-01 08:30:00', 0.0, 10.0, 240.0, '101 Elm St, Hamletville', 'DELIVER');

-- No more than 4 invoice items per invoice. Quantity must be always 1.
INSERT INTO InvoiceItem (invoiceId, equipmentId, quantity, rentalStartDate, rentalEndDate, totalPrice, approved)
VALUES (1, 'light1', 1, '2023-01-02 12:45:00', '2023-01-03 14:30:00', 50.0, TRUE),
       (1, 'sound1', 1, '2023-01-02 12:45:00', '2023-01-03 14:30:00', 100.0, TRUE),
       (2, 'stage2', 1, '2023-02-02 11:15:00', '2023-02-03 13:00:00', 80.0, FALSE),
       (3, 'power3', 1, '2023-03-02 10:20:00', '2023-03-03 11:45:00', 150.0, TRUE),
       (3, 'light3', 1, '2023-03-02 10:20:00', '2023-03-03 11:45:00', 200.0, TRUE),
       (4, 'sound4', 1, '2023-04-02 08:30:00', '2023-04-03 10:00:00', 120.0, FALSE);

-- Some users have rental requests, some do not
INSERT INTO RentalRequest (invoiceId, requestDate, customerId, approved, approvedBy, approvedDate)
VALUES (1, '2023-01-01 10:30:00', 'user1', TRUE, 'user9', '2023-01-02 12:45:00'),
       (2, '2023-02-01 11:15:00', 'user2', FALSE, NULL, NULL),
       (3, '2023-03-01 09:00:00', 'user3', TRUE, 'user10', '2023-03-02 10:20:00'),
       (4, '2023-04-01 08:30:00', 'user4', FALSE, NULL, NULL);

-- Some transactions for rental requests
INSERT INTO Transaction (balance, paid, transactionDate, requestId)
VALUES (150.0, 50.0, '2023-01-03 14:30:00', 1),
       (80.0, 80.0, '2023-02-03 13:00:00', 2),
       (200.0, 200.0, '2023-03-03 11:45:00', 3),
       (120.0, 0.0, '2023-04-03 10:00:00', 4);
