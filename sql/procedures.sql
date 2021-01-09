USE bezglutex;

DELIMITER $$
CREATE OR REPLACE FUNCTION realize_order(_order_id INT) RETURNS BOOLEAN
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE product, order_quantity INT;
    DECLARE storage_quantity INT;
    DECLARE products_in_order CURSOR FOR
        (SELECT product_id, quantity
         FROM orders_products
         WHERE order_id = _order_id);
    DECLARE CONTINUE HANDLER
        FOR NOT FOUND SET done = 1;
    OPEN products_in_order;
    update_loop:
    LOOP
        FETCH products_in_order INTO product, order_quantity;
        IF done THEN
            LEAVE update_loop;
        END IF;
        SET storage_quantity = (SELECT quantity FROM storage WHERE product_id = product);
        IF order_quantity > storage_quantity THEN
            RETURN FALSE;
        END IF;
    END LOOP;
    CLOSE products_in_order;

    UPDATE orders
    SET realized = TRUE,
        date     = (SELECT NOW())
    WHERE order_id = _order_id;

    RETURN TRUE;
END$$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE add_supplier(IN name VARCHAR(45), IN NIP VARCHAR(16), IN country VARCHAR(45),
                                         IN city VARCHAR(45), IN street VARCHAR(45), IN postal_code VARCHAR(16), IN email VARCHAR(120))
BEGIN
    SET @str = 'INSERT INTO suppliers (name, NIP, country, city, street, postal_code, email) VALUES (?, ?, ?, ?, ?, ?, ?);';

    PREPARE stmt FROM @str;
    EXECUTE stmt USING name, NIP, country, city, street, postal_code, email;
    DEALLOCATE PREPARE stmt;

END;$$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE add_supply(IN _supplier_id INT, IN _payment VARCHAR(16))
BEGIN
    INSERT INTO supplies (supplier_id, date, payment) VALUES (_supplier_id, (SELECT NOW()), _payment);
END;$$

DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE add_breadstuff(IN _type VARCHAR(32), IN _netto_weight INT, IN _pieces_per_package INT,
                                           IN _energy_value INT, IN _price DECIMAL(6, 2), IN _supply_id INT,
                                           IN _quantity INT)
BEGIN
    DECLARE _exists BOOLEAN DEFAULT FALSE;
    SET @_product_id = NULL;

    SELECT b.product_id
    INTO @_product_id
    FROM breadstuff b
             JOIN products p ON b.product_id = p.product_id
    WHERE b.type = _type
      AND netto_weight = _netto_weight
      AND pieces_per_package = _pieces_per_package
      AND energy_value = _energy_value
      AND price = _price;

    IF @_product_id IS NOT NULL THEN
        SET _exists = TRUE;
    END IF;

    CALL add_product(@_product_id, 'breadstuff', _price, _supply_id, _quantity, _exists);

    IF _exists = FALSE THEN
        INSERT INTO breadstuff VALUES (@_product_id, _type, _netto_weight, _pieces_per_package, _energy_value);
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE add_pasta(IN _type VARCHAR(32), IN _netto_weight INT, IN _energy_value INT,
                                      IN _boil_time INT, IN _price DECIMAL(6, 2), IN _supply_id INT,
                                      IN _quantity INT)
BEGIN
    DECLARE _exists BOOLEAN DEFAULT FALSE;
    SET @_product_id = NULL;

    SELECT p.product_id
    INTO @_product_id
    FROM pasta p
             JOIN products pr ON p.product_id = pr.product_id
    WHERE p.type = _type
      AND netto_weight = _netto_weight
      AND energy_value = _energy_value
      AND boil_time = _boil_time
      AND price = _price;

    IF @_product_id IS NOT NULL THEN
        SET _exists = TRUE;
    END IF;

    CALL add_product(@_product_id, 'pasta', _price, _supply_id, _quantity, _exists);

    IF _exists = FALSE THEN
        INSERT INTO pasta VALUES (@_product_id, _type, _netto_weight, _boil_time, _energy_value);
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE add_desserts(IN _name VARCHAR(45), IN _dairy_free BOOLEAN, IN _netto_weight INT,
                                         IN _energy_value INT, IN _price DECIMAL(6, 2), IN _supply_id INT,
                                         IN _quantity INT)
BEGIN
    DECLARE _exists BOOLEAN DEFAULT FALSE;
    SET @_product_id = NULL;

    SELECT d.product_id
    INTO @_product_id
    FROM desserts d
             JOIN products p ON d.product_id = p.product_id
    WHERE d.name = _name
      AND dairy_free = _dairy_free
      AND netto_weight = _netto_weight
      AND energy_value = _energy_value
      AND price = _price;

    IF @_product_id IS NOT NULL THEN
        SET _exists = TRUE;
    END IF;

    CALL add_product(@_product_id, 'dessert', _price, _supply_id, _quantity, _exists);

    IF _exists = FALSE THEN
        INSERT INTO desserts VALUES (@_product_id, _name, _dairy_free, _netto_weight, _energy_value);
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE add_product(INOUT _product_id INT, IN _type ENUM ('breadstuff', 'pasta', 'dessert'),
                                        IN _price DECIMAL(6, 2), IN _supply_id INT, IN _quantity INT,
                                        IN _exists BOOLEAN)
BEGIN
    IF _exists = FALSE THEN
        INSERT INTO products(type, price) VALUES (_type, _price);
        SET _product_id = LAST_INSERT_ID();
        INSERT INTO storage VALUES (_product_id, _quantity);
    ELSE
        UPDATE storage s
        SET s.quantity = s.quantity + _quantity
        WHERE product_id = _product_id;
    END IF;

    INSERT INTO supplies_products VALUES (_supply_id, _product_id, _quantity);
END$$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE reset_password(IN _user_id INT)
BEGIN
    UPDATE users
    SET password = (SELECT SHA(SUBSTRING(SHA(RAND()), 1, 20)))
    WHERE user_id = _user_id;
END $$
DELIMITER ;