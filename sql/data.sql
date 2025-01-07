-- component_category
INSERT INTO component_category (label) VALUES 
('Processor'),
('Motherboard'),
('RAM'),
('Graphics Card'),
('Power Supply'),
('Storage Drive'),
('Cooling System'),
('Case'),
('Sound Card'),
('Network Adapter');

-- model_category
INSERT INTO model_category (label) VALUES 
('Laptop'),
('Desktop'),
('Server'),
('Tablet'),
('Smartphone'),
('Smartwatch'),
('Gaming Console'),
('Network Equipment'),
('Printer'),
('Camera');

-- brand
INSERT INTO brand (label) VALUES 
('Dell'),
('HP'),
('Lenovo'),
('Apple'),
('Asus'),
('Acer'),
('Samsung'),
('Sony'),
('Microsoft'),
('LG');

-- component
INSERT INTO component (serial_number, id_component_category, id_model_category, id_brand, description) VALUES 
('SN12345', 1, 1, 1, 'Intel i7 Processor for Laptops'),
('SN12346', 2, 2, 2, 'Gaming Motherboard for Desktops'),
('SN12347', 3, 3, 3, '16GB DDR4 RAM for Servers'),
('SN12348', 4, 4, 4, 'NVIDIA Graphics Card for Tablets'),
('SN12349', 5, 5, 5, '600W Power Supply for Smartphones'),
('SN12350', 6, 6, 6, '1TB SSD for Smartwatches'),
('SN12351', 7, 7, 7, 'Water Cooling System for Gaming Consoles'),
('SN12352', 8, 8, 8, 'Gaming Case for Network Equipment'),
('SN12353', 9, 9, 9, 'High-Quality Sound Card for Printers'),
('SN12354', 10, 10, 10, 'WiFi Network Adapter for Cameras');


-- model
INSERT INTO model (serial_number, id_model_category, id_brand, description) VALUES 
('M12345', 1, 1, 'Laptop Model A from Dell'),
('M12346', 2, 2, 'Desktop Model B from HP'),
('M12347', 3, 3, 'Server Model C from Lenovo'),
('M12348', 4, 4, 'Tablet Model D from Apple'),
('M12349', 5, 5, 'Smartphone Model E from Asus'),
('M12350', 6, 6, 'Smartwatch Model F from Acer'),
('M12351', 7, 7, 'Gaming Console Model G from Samsung'),
('M12352', 8, 8, 'Network Equipment Model H from Sony'),
('M12353', 9, 9, 'Printer Model I from Microsoft'),
('M12354', 10, 10, 'Camera Model J from LG');

-- reparation
INSERT INTO reparation (date, price, id_model) VALUES 
('2024-01-01', 150.00, 1),
('2024-01-02', 200.00, 2),
('2024-01-03', 250.00, 3),
('2024-01-04', 300.00, 4),
('2024-01-05', 350.00, 5),
('2024-01-06', 400.00, 6),
('2024-01-07', 450.00, 7),
('2024-01-08', 500.00, 8),
('2024-01-09', 550.00, 9),
('2024-01-10', 600.00, 10),
('2024-01-11', 150.00, 1),
('2024-01-12', 200.00, 2),
('2024-01-13', 250.00, 3),
('2024-01-14', 300.00, 4),
('2024-01-15', 350.00, 5),
('2024-01-16', 400.00, 6),
('2024-01-17', 450.00, 7),
('2024-01-18', 500.00, 8),
('2024-01-19', 550.00, 9),
('2024-01-20', 600.00, 10),
('2024-01-21', 150.00, 1),
('2024-01-22', 200.00, 2),
('2024-01-23', 250.00, 3),
('2024-01-24', 300.00, 4),
('2024-01-25', 350.00, 5);

-- reparation_detail
INSERT INTO reparation_detail (id_reparation, id_component_category) VALUES 
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 1),
(12, 2),
(13, 3),
(14, 4),
(15, 5),
(16, 6),
(17, 7),
(18, 8),
(19, 9),
(20, 10),
(21, 1),
(22, 2),
(23, 3),
(24, 4),
(25, 5);


