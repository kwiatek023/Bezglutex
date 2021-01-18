DELETE FROM users;
DELETE FROM supplies;
DELETE FROM orders_products;
DELETE FROM orders;
DELETE FROM customers;
DELETE FROM storage;
DELETE FROM breadstuff;
DELETE FROM pasta;
DELETE FROM desserts;
DELETE FROM products;

DELIMITER $$
DROP PROCEDURE IF EXISTS create_customers;
CREATE PROCEDURE create_customers()
BEGIN
    DECLARE i INT UNSIGNED DEFAULT 0;
    DECLARE _firstname VARCHAR(45);
    DECLARE _lastname VARCHAR(45);
    DECLARE _NIP VARCHAR(16);
    DECLARE _country VARCHAR(45) DEFAULT 'Poland';
    DECLARE _city VARCHAR(45) DEFAULT 'Warsaw';
    DECLARE _street VARCHAR(45);
    DECLARE _postal_code VARCHAR(16) DEFAULT '00-200';
    DECLARE _email VARCHAR(120);
    ALTER TABLE customers AUTO_INCREMENT = 1;

    WHILE i < 15
        DO
            SET _firstname = ELT(FLOOR(1 + RAND() * (13 - 1 + 1)), 'Alicja', 'Jan', 'Jakub', 'Zbigniew', 'Maciej',
                                 'Oliwia', 'Edmund', 'Jarosław', 'Andrzej', 'Beata', 'Anna', 'Paweł', 'Karolina');
            SET _lastname =
                    ELT(FLOOR(1 + RAND() * (13 - 1 + 1)), 'Nowak', 'Kowalczyk', 'Hołoś', 'Pietrusiak', 'Kaczmarczyk',
                        'Wróbel', 'Stach', 'Śleziak', 'Sawczyn', 'Piech', 'Mazur', 'Stoch', 'Małysz');
            SET _NIP = CONCAT('PL ',
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             '-',
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             '-',
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             '-',
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1));
            SET _street = CONCAT( ELT(FLOOR(1 + RAND() * (13 - 1 + 1)), 'Nowa', 'Długa', 'Krótka', 'Poziomkowa', 'Piękna',
                        'Kwiatowa', 'Kolorowa', 'Dzika', 'Śliczna', 'Lazurowa', 'Mikorska', 'Topolowa', 'Prusa'), ' ', SUBSTRING('123456789', FLOOR(1 + RAND() * (10 - 1 + 1)), FLOOR(1 + RAND() * (2 - 1 + 1))));

            SET _email = CONCAT(LOWER(_firstname), '.', LOWER(_lastname), '@gmail.com');
            INSERT INTO customers(firstname, lastname, NIP, country, city, street, postal_code, email) VALUES (_firstname, _lastname, _NIP, _country, _city, _street, _postal_code, _email);
            SET i = i + 1;
        END WHILE;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS create_suppliers;
CREATE PROCEDURE create_suppliers()
BEGIN
    DECLARE i INT UNSIGNED DEFAULT 0;
    DECLARE _name VARCHAR(45);
    DECLARE _NIP VARCHAR(16);
    DECLARE _country VARCHAR(45) DEFAULT 'Poland';
    DECLARE _city VARCHAR(45) DEFAULT 'Warsaw';
    DECLARE _street VARCHAR(45);
    DECLARE _postal_code VARCHAR(16) DEFAULT '00-200';
    DECLARE _email VARCHAR(120);
    ALTER TABLE suppliers AUTO_INCREMENT = 1;

    WHILE i < 15
        DO
            SET _name = ELT(i + 1, 'Hert', 'JedzBezGlutenu', 'PrzekreślonyKłos', 'Balviten', 'Incola',
                        'Goplana', 'GlutenFree', 'NaturalFood', 'Huel', 'SmakiDiet', 'MazurWorld', 'MaciekWorld', 'GlutenDetect', 'PolishFood', 'NoGluten');
            SET _NIP = CONCAT('PL ',
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             '-',
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             '-',
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             '-',
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1),
                             SUBSTRING('1234567890', FLOOR(1 + RAND() * (10 - 1 + 1)), 1));
            SET _street = CONCAT( ELT(FLOOR(1 + RAND() * (13 - 1 + 1)), 'Nowa', 'Długa', 'Krótka', 'Poziomkowa', 'Piękna',
                        'Kwiatowa', 'Kolorowa', 'Dzika', 'Śliczna', 'Lazurowa', 'Mikorska', 'Topolowa', 'Prusa'), ' ', SUBSTRING('123456789', FLOOR(1 + RAND() * (10 - 1 + 1)), FLOOR(1 + RAND() * (2 - 1 + 1))));

            SET _email = CONCAT(LOWER(_name), '@gmail.com');
            CALL add_supplier(_name, _NIP, _country, _city, _street, _postal_code, _email, @id);
            SET i = i + 1;
        END WHILE;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS create_supplies;
CREATE PROCEDURE create_supplies()
BEGIN
    DECLARE i INT UNSIGNED DEFAULT 0;
    DECLARE _supplier_id INT;
    DECLARE _payment ENUM('bank_transfer', 'on_delivery');
    ALTER TABLE supplies AUTO_INCREMENT = 1;

    WHILE i < 25
        DO
            SET _supplier_id = (i % 15) + 1;
            SET _payment = ELT(FLOOR(1 + RAND() * (2 - 1 + 1)), 'bank_transfer', 'on_delivery');
            CALL add_supply(_supplier_id, _payment, @id);
            SET i = i + 1;
    END WHILE;

END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE create_breadstuff()
BEGIN
    DECLARE i INT UNSIGNED DEFAULT 0;
    DECLARE _type VARCHAR(32);
    DECLARE _netto_weight INT;
    DECLARE _pieces_per_package INT;
    DECLARE _energy_value INT;
    DECLARE _price DECIMAL(6, 2);
    DECLARE _supply_id INT;
    DECLARE _quantity INT;
    ALTER TABLE products AUTO_INCREMENT = 1;

    WHILE i < 30
        DO
            SET _type = ELT(FLOOR(1 + RAND() * (7 - 1 + 1)), 'baguette', 'white_bread', 'dark_bread', 'granary_bread',
                                                                'toasted_bread', 'kaiser_roll', 'ciabatta');
            SET _netto_weight = FLOOR(100 + RAND() * (700 - 100 + 1));
            SET _pieces_per_package = FLOOR(1 + RAND() * (6 - 1 + 1));
            SET _energy_value = FLOOR(50 + RAND() * (300 - 50 + 1));
            SET _price = FLOOR(RAND() * 16) + 0.99;
            SET _supply_id = i % 25 + 1;
            SET _quantity = FLOOR(50 + RAND() * (300 - 50 + 1));
            CALL add_breadstuff(_type, _netto_weight, _pieces_per_package, _energy_value,
                                _price, _supply_id, _quantity);
            SET i = i + 1;
            END WHILE;
END;$$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE create_pasta()
BEGIN
    DECLARE i INT UNSIGNED DEFAULT 0;
    DECLARE _type VARCHAR(32);
    DECLARE _netto_weight INT;
    DECLARE _energy_value INT;
    DECLARE _boil_time INT;
    DECLARE _price DECIMAL(6, 2);
    DECLARE _supply_id INT;
    DECLARE _quantity INT;
    ALTER TABLE products AUTO_INCREMENT = 1;

    WHILE i < 30
        DO
            SET _type = ELT(FLOOR(1 + RAND() * (5 - 1 + 1)), 'penne', 'filini', 'pipette', 'spaghetti', 'conchiglie');
            SET _netto_weight = FLOOR(100 + RAND() * (700 - 100 + 1));
            SET _boil_time = FLOOR(300 + RAND() * (900 - 300 + 1));
            SET _energy_value = FLOOR(50 + RAND() * (300 - 50 + 1));
            SET _price = FLOOR(RAND() * 16) + 0.99;
            SET _supply_id = i % 25 + 1;
            SET _quantity = FLOOR(50 + RAND() * (300 - 50 + 1));
            CALL add_pasta(_type, _netto_weight, _energy_value, _boil_time,
                                _price, _supply_id, _quantity);
            SET i = i + 1;
            END WHILE;
END;$$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE create_desserts()
BEGIN
    DECLARE i INT UNSIGNED DEFAULT 0;
    DECLARE _name VARCHAR(45);
    DECLARE _dairy_free BOOLEAN;
    DECLARE _netto_weight INT;
    DECLARE _energy_value INT;
    DECLARE _price DECIMAL(6, 2);
    DECLARE _supply_id INT;
    DECLARE _quantity INT;
    ALTER TABLE products AUTO_INCREMENT = 1;

    WHILE i < 30
        DO
            SET _name = ELT(FLOOR(1 + RAND() * (6 - 1 + 1)), 'Ciastko Parkinsona', 'Pierożki babuni', 'Najmanki', 'Słodki nietoperz', 'Babka w koronie', 'Chipsy od Pfizera');
            SET _netto_weight = FLOOR(100 + RAND() * (700 - 100 + 1));
            SET _dairy_free = FLOOR(RAND() * 2);
            SET _energy_value = FLOOR(50 + RAND() * (300 - 50 + 1));
            SET _price = FLOOR(RAND() * 16) + 0.99;
            SET _supply_id = i % 25 + 1;
            SET _quantity = FLOOR(50 + RAND() * (300 - 50 + 1));
            CALL add_desserts(_name, _dairy_free, _netto_weight,
                    _energy_value, _price, _supply_id, _quantity);
            SET i = i + 1;
            END WHILE;
END;$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS create_orders;
CREATE PROCEDURE create_orders()
BEGIN
    DECLARE i INT UNSIGNED DEFAULT 0;
    DECLARE j INT UNSIGNED DEFAULT 0;
    DECLARE _customer_id INT;
    DECLARE _date date;
    DECLARE _payment ENUM('bank_transfer', 'on_delivery');
    DECLARE _realized BOOLEAN;
    DECLARE _order_id INT;
    DECLARE _product_id INT;
    DECLARE _quantity INT;
    ALTER TABLE orders AUTO_INCREMENT = 1;

    WHILE i < 20
        DO
            SET _customer_id = (i % 15) + 1;
            SET @rand = FLOOR(RAND() * 2);

            IF @rand = 0 THEN
                SET _date = NULL;
                SET _realized = FALSE;
            ELSE
                SET _date = (SELECT NOW() - INTERVAL FLOOR(RAND() * 14) DAY);
                SET _realized = TRUE;
            END IF;

            SET _payment = ELT(FLOOR(1 + RAND() * (2 - 1 + 1)), 'bank_transfer', 'on_delivery');
            INSERT INTO orders(customer_id, date, payment, realized) VALUES (_customer_id, _date, _payment, _realized);
            SET _order_id = LAST_INSERT_ID();

            SET @rand = FLOOR(1 + RAND() * (4 - 1 + 1));
            SET j = 0;

            WHILE j < @rand
                DO
                    SET _product_id = FLOOR(1 + RAND() * (90 - 1 + 1));
                    SET _quantity = (SELECT FLOOR(1 + RAND() * (5 - 1 + 1)));
                    INSERT INTO orders_products VALUES (_order_id, _product_id, _quantity);
                    SET j = j + 1;
            END WHILE;

            SET i = i + 1;
    END WHILE;
END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS create_users;
CREATE PROCEDURE create_users()
BEGIN
    ALTER TABLE users AUTO_INCREMENT = 1;

    INSERT INTO users VALUES
    ('zbigniew', 'qwerty', 'store_keeper'),
    ('maciej', 'maciej1', 'store_manager'),
    ('wiesia', 'wiesia1', 'salesman'),
    ('krzysztof', 'krzysztof1', 'admin');
END$$
DELIMITER ;

CALL create_customers();
CALL create_suppliers();
CALL create_supplies();
CALL create_breadstuff();
CALL create_pasta();
CALL create_desserts();
CALL create_orders();
CALL create_users();

DROP PROCEDURE create_customers;
DROP PROCEDURE create_suppliers;
DROP PROCEDURE create_supplies;
DROP PROCEDURE create_breadstuff;
DROP PROCEDURE create_pasta;
DROP PROCEDURE create_desserts;
DROP PROCEDURE create_orders;
DROP PROCEDURE create_users;