USE bezglutex;

#Users
DROP USER IF EXISTS 'salesman'@'localhost';
CREATE USER 'salesman'@'localhost' IDENTIFIED BY 'salesman';
DROP USER IF EXISTS 'admin'@'localhost';
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
DROP USER IF EXISTS 'store_keeper'@'localhost';
CREATE USER 'store_keeper'@'localhost' IDENTIFIED BY 'store_keeper';
DROP USER IF EXISTS 'store_manager'@'localhost';
CREATE USER 'store_manager'@'localhost' IDENTIFIED BY 'store_manager';

#salesman
GRANT SELECT, UPDATE ON orders TO 'salesman'@'localhost';
GRANT SELECT ON orders_products TO 'salesman'@'localhost';
GRANT SELECT ON products TO 'salesman'@'localhost';
GRANT SELECT ON breadstuff TO 'salesman'@'localhost';
GRANT SELECT ON pasta TO 'salesman'@'localhost';
GRANT SELECT ON desserts TO 'salesman'@'localhost';
GRANT SELECT, UPDATE ON storage TO 'salesman'@'localhost';
GRANT SELECT ON customers TO 'salesman'@'localhost';

GRANT EXECUTE ON FUNCTION realize_order TO 'salesman'@'localhost';

#store_keeper
GRANT SELECT, INSERT, UPDATE ON storage TO 'store_keeper'@'localhost';
GRANT SELECT, INSERT ON products TO 'store_keeper'@'localhost';
GRANT SELECT, INSERT ON breadstuff TO 'store_keeper'@'localhost';
GRANT SELECT, INSERT ON pasta TO 'store_keeper'@'localhost';
GRANT SELECT, INSERT ON desserts TO 'store_keeper'@'localhost';
GRANT SELECT, INSERT ON supplies_products TO 'store_keeper'@'localhost';
GRANT SELECT, INSERT ON supplies TO 'store_keeper'@'localhost';
GRANT SELECT, INSERT ON suppliers TO 'store_keeper'@'localhost';

GRANT EXECUTE ON PROCEDURE add_supply TO 'store_keeper'@'localhost';
GRANT EXECUTE ON PROCEDURE add_supplier TO 'store_keeper'@'localhost';
GRANT EXECUTE ON PROCEDURE add_product TO 'store_keeper'@'localhost';
GRANT EXECUTE ON PROCEDURE add_breadstuff TO 'store_keeper'@'localhost';
GRANT EXECUTE ON PROCEDURE add_pasta TO 'store_keeper'@'localhost';
GRANT EXECUTE ON PROCEDURE add_desserts TO 'store_keeper'@'localhost';

#store_manager
GRANT SELECT, INSERT, UPDATE ON storage TO 'store_manager'@'localhost';
GRANT SELECT, INSERT ON products TO 'store_manager'@'localhost';
GRANT SELECT, INSERT ON breadstuff TO 'store_manager'@'localhost';
GRANT SELECT, INSERT ON pasta TO 'store_manager'@'localhost';
GRANT SELECT, INSERT ON desserts TO 'store_manager'@'localhost';
GRANT SELECT, INSERT, DELETE ON supplies_products TO 'store_manager'@'localhost';
GRANT SELECT, INSERT, DELETE ON supplies TO 'store_manager'@'localhost';
GRANT SELECT, INSERT, DELETE ON suppliers TO 'store_manager'@'localhost';

GRANT EXECUTE ON PROCEDURE add_supply TO'store_manager'@'localhost';
GRANT EXECUTE ON PROCEDURE add_supplier TO'store_manager'@'localhost';
GRANT EXECUTE ON PROCEDURE add_product TO'store_manager'@'localhost';
GRANT EXECUTE ON PROCEDURE add_breadstuff TO'store_manager'@'localhost';
GRANT EXECUTE ON PROCEDURE add_pasta TO'store_manager'@'localhost';
GRANT EXECUTE ON PROCEDURE add_desserts TO'store_manager'@'localhost';

#admin
GRANT ALL PRIVILEGES ON bezglutex.* TO 'admin'@'localhost';