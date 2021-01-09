USE bezglutex;

DELIMITER $$
CREATE OR REPLACE TRIGGER on_supplies_delete
    BEFORE DELETE
    ON supplies
    FOR EACH ROW
BEGIN
    IF (SELECT COUNT(*) FROM supplies WHERE supplier_id = OLD.supplier_id) <= 1 THEN
        DELETE FROM suppliers WHERE suppliers.supplier_id = OLD.supplier_id;
    END IF;

    UPDATE storage s
        JOIN supplies_products sp ON s.product_id = sp.product_id
    SET s.quantity = s.quantity - sp.quantity
    WHERE sp.supply_id = OLD.supply_id;

    DELETE
    FROM supplies_products
    WHERE supplies_products.supply_id = OLD.supply_id;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE TRIGGER on_users_insert
    BEFORE INSERT
    ON users
    FOR EACH ROW
BEGIN
    SET NEW.password = sha(NEW.password);
END;
$$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE TRIGGER on_orders_update
    BEFORE UPDATE
    ON orders
    FOR EACH ROW
BEGIN
    IF OLD.realized = FALSE AND NEW.realized = TRUE THEN
        UPDATE storage s
            JOIN orders_products op ON s.product_id = op.product_id
        SET s.quantity = s.quantity - op.quantity
        WHERE op.order_id = NEW.order_id;
    END IF;
END;
$$
DELIMITER ;