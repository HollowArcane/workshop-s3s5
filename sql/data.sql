-- component_category
INSERT INTO component_category (id, label) VALUES 
(1, 'Processor'),
(2, 'Motherboard'),
(3, 'RAM'),
(4, 'Graphics Card'),
(5, 'Power Supply'),
(6, 'Storage Drive'),
(7, 'Cooling System'),
(8, 'Case'),
(9, 'Sound Card'),
(10, 'Network Adapter');

-- model_category
INSERT INTO model_category (id, label) VALUES 
(1, 'Laptop'),
(2, 'Desktop'),
(3, 'Server'),
(4, 'Tablet'),
(5, 'Smartphone'),
(6, 'Smartwatch'),
(7, 'Gaming Console'),
(8, 'Network Equipment'),
(9, 'Printer'),
(10,'Camera');

-- brand
INSERT INTO brand (id, label) VALUES 
(1, 'Dell'),
(2, 'HP'),
(3, 'Lenovo'),
(4, 'Apple'),
(5, 'Asus'),
(6, 'Acer'),
(7, 'Samsung'),
(8, 'Sony'),
(9, 'Microsoft'),
(10,'LG');

-- component
INSERT INTO component (id, serial_number, id_component_category, id_model_category, id_brand, description) VALUES 
(1,  'SN12345', 1, 1, 1, 'Intel i7 Processor for Laptops'),
(2,  'SN12346', 2, 2, 2, 'Gaming Motherboard for Desktops'),
(3,  'SN12347', 3, 3, 3, '16GB DDR4 RAM for Servers'),
(4,  'SN12348', 4, 4, 4, 'NVIDIA Graphics Card for Tablets'),
(5,  'SN12349', 5, 5, 5, '600W Power Supply for Smartphones'),
(6,  'SN12350', 6, 6, 6, '1TB SSD for Smartwatches'),
(7,  'SN12351', 7, 7, 7, 'Water Cooling System for Gaming Consoles'),
(8,  'SN12352', 8, 8, 8, 'Gaming Case for Network Equipment'),
(9,  'SN12353', 9, 9, 9, 'High-Quality Sound Card for Printers'),
(10, 'SN12354', 10, 10, 10, 'WiFi Network Adapter for Cameras');


-- model
INSERT INTO model (id, serial_number, id_model_category, id_brand, description) VALUES 
(1,  'M12345', 1, 1, 'Laptop Model A from Dell'),
(2,  'M12346', 2, 2, 'Desktop Model B from HP'),
(3,  'M12347', 3, 3, 'Server Model C from Lenovo'),
(4,  'M12348', 4, 4, 'Tablet Model D from Apple'),
(5,  'M12349', 5, 5, 'Smartphone Model E from Asus'),
(6,  'M12350', 6, 6, 'Smartwatch Model F from Acer'),
(7,  'M12351', 7, 7, 'Gaming Console Model G from Samsung'),
(8,  'M12352', 8, 8, 'Network Equipment Model H from Sony'),
(9,  'M12353', 9, 9, 'Printer Model I from Microsoft'),
(10, 'M12354', 10, 10, 'Camera Model J from LG');

-- engineer

-- Insertion de techniciens
INSERT INTO engineer (id, name, telephone, email, address, id_gender) VALUES
(1, 'Alice Dupont', '+33123456789', 'alice.dupont@example.com', '123 Rue de Paris, 75001 Paris, France', 2),
(2, 'Bob Martin', '+441234567890', 'bob.martin@example.co.uk', '456 Oxford Street, London, UK', 1),
(3, 'Charlie Nguyen', '+4915123456789', 'charlie.nguyen@example.de', '789 Berliner Str., Berlin, Germany', 2),
(4, 'Dana Lee', '+8613912345678', 'dana.lee@example.cn', '101 Zhongguancun, Beijing, China', 1),
(5, 'Ethan Brown', '+18191234567', 'ethan.brown@example.com', '202 Elm Street, New York, USA', 1);


-- reparation
INSERT INTO ticket (id, id_engineer, price_reparation, id_customer, id_model, diagnostic, id_ticket_state, date_start, date_end)
    VALUES 
(1, 1, 250.00, 1, 1, 'Processor overheating, requires cooling', 1, '2025-01-01 10:00:00', '2025-01-03 15:00:00'),
(2, 2, 150.00, 2, 2, 'BIOS issue, motherboard replacement', 2, '2025-01-05 11:00:00', NULL),
(3, 3, 75.00, 3, 3, 'Memory module diagnostics', 3, '2025-01-10 14:30:00', NULL),
(4, 4, 500.00, 4, 4, 'GPU rendering artifacts, repair needed', 5, '2025-01-15 09:00:00', '2025-01-20 18:00:00');

INSERT INTO mvt_ticket_state(id, id_ticket, id_ticket_state, datetime)
    VALUES
(1, 1, 5, '2025-01-03 15:00:00'),
(2, 4, 5, '2025-01-20 18:00:00');

INSERT INTO ticket_component (id, id_ticket, id_component, quantity, cost_total)
VALUES 
    (1, 1, 1, 1.00, 250.00),
    (2, 2, 2, 1.00, 150.00),
    (3, 3, 3, 2.00, 75.00),
    (4, 4, 4, 1.00, 500.00),
    (5, 4, 5, 1.00, 1000.00),
    (6, 2, 5, 2.00, 2000.00);

-- Insertion de données dans la table `recommendation_component`
INSERT INTO recommendation_component (id_component, date_start, date_end) VALUES 
    (1, '2025-01-01', '2025-01-15'),
    (2, '2025-01-16', '2025-01-31'),
    (3, '2025-02-01', '2025-02-15'),
    (4, '2025-02-16', '2025-02-28'),
    (5, '2025-03-01', '2025-03-15');


-- Insertion de données dans la table customer
INSERT INTO customer (id, name, telephone, email, address)
VALUES
(1, 'Alice Dupont', '+33123456789', 'alice.dupont@example.com', '123 Rue de Paris, Paris'),
(2, 'Bob Martin', '+447123456789', 'bob.martin@example.co.uk', '45 Baker Street, London'),
(3 ,'Carla Garcia', '+34912345678', 'carla.garcia@example.es', '12 Calle Mayor, Madrid'),
(4, 'David Brown', '+12125551234', 'david.brown@example.com', '100 Main Street, New York'),
(5, 'Eva Schmidt', '+4915123456789', 'eva.schmidt@example.de', '56 Hauptstrasse, Berlin');

-- Exemple avec des valeurs optionnelles (téléphone et email peuvent être NULL)
INSERT INTO customer (id, name, telephone, email, address)
VALUES
(6, 'François Leclerc', NULL, 'francois.leclerc@example.fr', '789 Boulevard Saint-Michel, Paris'),
(7, 'Gina Rossi', '+390612345678', NULL, 'Via Roma 32, Rome');