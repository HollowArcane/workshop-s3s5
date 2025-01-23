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

-- reparation
INSERT INTO reparation (id, date, price, id_model, id_engineer) VALUES
(1,  '2024-01-01', 25000.00, 1, 1),
(2,  '2024-01-02', 275000.00, 2, 2),
(3,  '2024-01-03', 30000.00, 3, 3),
(4,  '2024-01-04', 350000.00, 4, 4),
(5,  '2024-01-05', 400000.00, 5, 5),
(6,  '2024-01-06', 45000.00, 1, 1),
(7,  '2024-01-07', 500000.00, 2, 2),
(8,  '2024-01-08', 55000.00, 3, 3),
(9,  '2024-01-09', 600000.00, 4, 4),
(10, '2024-01-10', 650000.00, 5, 5),
(11, '2024-01-11', 22500.00, 1, 1),
(12, '2024-01-12', 260000.00, 2, 2),
(13, '2024-01-13', 29000.00, 3, 3),
(14, '2024-01-14', 320000.00, 4, 4),
(15, '2024-01-15', 38000.00, 5, 5),
(16, '2024-01-16', 420000.00, 1, 1),
(17, '2024-01-17', 47000.00, 2, 2),
(18, '2024-01-18', 520000.00, 3, 3),
(19, '2024-01-19', 57000.00, 4, 4),
(20, '2024-01-20', 620000.00, 5, 5),
(21, '2024-01-21', 24000.00, 1, 1),
(22, '2024-01-22', 280000.00, 2, 2),
(23, '2024-01-23', 310000.00, 3, 3),
(24, '2024-01-24', 34000.00, 4, 4),
(25, '2024-01-25', 390000.00, 5, 5);


-- reparation_detail
INSERT INTO reparation_detail (id, id_reparation, id_component_category) VALUES 
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5),
(6, 6, 6),
(7, 7, 7),
(8, 8, 8),
(9, 9, 9),
(10, 10, 10),
(11, 11, 1),
(12, 12, 2),
(13, 13, 3),
(14, 14, 4),
(15, 15, 5),
(16, 16, 6),
(17, 17, 7),
(18, 18, 8),
(19, 19, 9),
(20, 20, 10),
(21, 21, 1),
(22, 22, 2),
(23, 23, 3),
(24, 24, 4),
(25, 25, 5);


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


-- Insertion de données dans la table reparation_feedback
INSERT INTO reparation_feedback (id, date, id_customer, id_reparation) VALUES
(1, '2024-02-01', 1, 1),
(2, '2024-02-02', 2, 2),
(3, '2024-02-03', 3, 3),
(4, '2024-02-04', 4, 4),
(5, '2024-02-05', 5, 5),
(6, '2024-02-06', 1, 6),
(7, '2024-02-07', 2, 7),
(8, '2024-02-08', 3, 8),
(9, '2024-02-09', 4, 9),
(10, '2024-02-10', 5, 10),
(11, '2024-02-11', 1, 11),
(12, '2024-02-12', 2, 12),
(13, '2024-02-13', 3, 13),
(14, '2024-02-14', 4, 14),
(15, '2024-02-15', 5, 15);


-- Insertion de genre
INSERT INTO gender (id, gender) VALUES 
(1, 'Homme'),
(2, 'Femme');



-- Insertion de techniciens
INSERT INTO engineer (id, name, telephone, email, address, id_gender) VALUES
(1, 'Alice Dupont', '+33123456789', 'alice.dupont@example.com', '123 Rue de Paris, 75001 Paris, France', 2),
(2, 'Bob Martin', '+441234567890', 'bob.martin@example.co.uk', '456 Oxford Street, London, UK', 1),
(3, 'Charlie Nguyen', '+4915123456789', 'charlie.nguyen@example.de', '789 Berliner Str., Berlin, Germany', 2),
(4, 'Dana Lee', '+8613912345678', 'dana.lee@example.cn', '101 Zhongguancun, Beijing, China', 1),
(5, 'Ethan Brown', '+18191234567', 'ethan.brown@example.com', '202 Elm Street, New York, USA', 1);
