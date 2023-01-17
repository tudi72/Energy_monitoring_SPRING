DELETE FROM `energy_consumption`;
DELETE FROM `my_user_devices`;
DELETE FROM `device`;
DELETE FROM `my_user_roles`;
DELETE FROM `role`;
DELETE FROM `my_user`;
DELETE FROM `hibernate_sequence`;
INSERT INTO `device` VALUES (1,'Cluj-Napoca','device1',0.4),(2,'Cluj-Napoca','device2',0.4),(3,'Cluj-Napoca','device3',0.4);
INSERT INTO `hibernate_sequence` VALUES (23),(23),(23),(23);
INSERT INTO `my_user` VALUES 
(1,'address',18,'admin 1 name','$2a$10$hvUqfcG0Zf7wb1IzSR8qUOUY8TtP7iHVdTBv/3Or60RixIfY/jluG','admin'),
(2,'address',18,'admin 2 name','$2a$12$XMa1YzaU5r5L.Ufa.Ppk7.6eFQU/TCbtbKxL5ViGKX9O0QjWVa1Gq','admin2'),
(3,'address',18,'client 1 name','$2a$10$D4Tyd/rCN6H9QWCp6tcnUOLgZ0xUjqCCEMXPwwnNCn/8kcEl26utq','client'),
(4,'address',18,'client 2 name','$2a$12$XMa1YzaU5r5L.Ufa.Ppk7.6eFQU/TCbtbKxL5ViGKX9O0QjWVa1Gq','client2');
INSERT INTO `role` VALUES 
(1,'ROLE_ADMIN'),
(2,'ROLE_CLIENT');
INSERT INTO `my_user_roles` VALUES (1,1),(2,1),(3,2),(4,2);
show tables;