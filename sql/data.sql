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
INSERT INTO reparation (id, date, price, id_model) VALUES 
(1,  '2024-01-01', 150.00, 1),
(2,  '2024-01-02', 200.00, 2),
(3,  '2024-01-03', 250.00, 3),
(4,  '2024-01-04', 300.00, 4),
(5,  '2024-01-05', 350.00, 5),
(6,  '2024-01-06', 400.00, 6),
(7,  '2024-01-07', 450.00, 7),
(8,  '2024-01-08', 500.00, 8),
(9,  '2024-01-09', 550.00, 9),
(10, '2024-01-10', 600.00, 10),
(11,  '2024-01-11', 150.00, 1),
(12,  '2024-01-12', 200.00, 2),
(13,  '2024-01-13', 250.00, 3),
(14,  '2024-01-14', 300.00, 4),
(15,  '2024-01-15', 350.00, 5),
(16,  '2024-01-16', 400.00, 6),
(17,  '2024-01-17', 450.00, 7),
(18,  '2024-01-18', 500.00, 8),
(19,  '2024-01-19', 550.00, 9),
(20, '2024-01-20', 600.00, 10),
(21, '2024-01-21', 150.00, 1),
(22, '2024-01-22', 200.00, 2),
(23, '2024-01-23', 250.00, 3),
(24, '2024-01-24', 300.00, 4),
(25, '2024-01-25', 350.00, 5);

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


-- reparation_feedback
INSERT INTO reparation_feedback (id, date, id_reparation) VALUES
(1, '2024-02-01', 1),
(2, '2024-02-02', 2),
(3, '2024-02-03', 3),
(4, '2024-02-04', 4),
(5, '2024-02-05', 5),
(6, '2024-02-06', 6),
(7, '2024-02-07', 7),
(8, '2024-02-08', 8),
(9, '2024-02-09', 9),
(10, '2024-02-10', 10),
(11, '2024-02-11', 11),
(12, '2024-02-12', 12),
(13, '2024-02-13', 13),
(14, '2024-02-14', 14),
(15, '2024-02-15', 15);


-- Insertion de données dans la table `recommendation_component`
INSERT INTO recommendation_component (id_component, date_start, date_end) VALUES 
    (1, '2025-01-01', '2025-01-15'),
    (2, '2025-01-16', '2025-01-31'),
    (3, '2025-02-01', '2025-02-15'),
    (4, '2025-02-16', '2025-02-28'),
    (5, '2025-03-01', '2025-03-15');


