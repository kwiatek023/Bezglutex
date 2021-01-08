DROP
DATABASE IF EXISTS bezglutex;
CREATE
DATABASE bezglutex;
USE
bezglutex;

CREATE TABLE customers
(
    customer_id INT          NOT NULL AUTO_INCREMENT,
    firstname   VARCHAR(45)  NOT NULL,
    lastname    VARCHAR(45)  NOT NULL,
    NIP         VARCHAR(16)  NOT NULL,
    country     VARCHAR(45)  NOT NULL,
    city        VARCHAR(45)  NOT NULL,
    street      VARCHAR(45)  NOT NULL,
    postal_code VARCHAR(16)  NOT NULL,
    email       VARCHAR(120) NOT NULL,

    CONSTRAINT customers_pk PRIMARY KEY (customer_id)
);

CREATE TABLE orders
(
    order_id    INT     NOT NULL AUTO_INCREMENT,
    customer_id INT     NOT NULL,
    date        DATE,
    payment     ENUM ('bank_transfer', 'on_delivery') NOT NULL,
    realized    BOOLEAN NOT NULL,

    CONSTRAINT orders_pk PRIMARY KEY (order_id),
    CONSTRAINT customers_fk FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
);

CREATE TABLE orders_products
(
    order_id   INT NOT NULL,
    product_id INT NOT NULL,
    quantity   INT NOT NULL,

    CONSTRAINT orders_products_pk PRIMARY KEY (order_id, product_id),
    CONSTRAINT orders_fk FOREIGN KEY (order_id) REFERENCES orders (order_id),
    CONSTRAINT products_fk FOREIGN KEY (product_id) REFERENCES products (product_id),
    CONSTRAINT nonnegative_quantity CHECK (quantity >= 0)
);

CREATE TABLE products
(
    product_id INT           NOT NULL AUTO_INCREMENT,
    type       ENUM ('breadstuff', 'pasta', 'dessert') NOT NULL,
    price      DECIMAL(6, 2) NOT NULL,

    CONSTRAINT products_pk PRIMARY KEY (product_id),
    CONSTRAINT nonnegative_price CHECK (price >= 0)
);

CREATE TABLE breadstuff
(
    product_id         INT NOT NULL,
    type               ENUM ('baguette', 'white bread',
        'dark bread', 'granary bread',
        'toasted bread', 'kaiser roll',
        'ciabatta', 'other') NOT NULL,

    netto_weight       INT NOT NULL,
    pieces_per_package INT NOT NULL,
    energy_value       INT NOT NULL,

    CONSTRAINT breadstuff_pk PRIMARY KEY (product_id),
    CONSTRAINT products_fk FOREIGN KEY (product_id) REFERENCES products (product_id),
    CONSTRAINT nonnegative_netto_weight CHECK (netto_weight >= 0),
    CONSTRAINT nonnegative_pieces_per_package CHECK (pieces_per_package >= 0),
    CONSTRAINT nonnegative_energy_value CHECK (energy_value >= 0)
);

CREATE TABLE pasta
(
    product_id   INT NOT NULL,
    type         ENUM ('penne', 'filini', 'pipette',
        'spaghetti', 'conchiglie', 'other') NOT NULL,

    netto_weight INT NOT NULL,
    energy_value INT NOT NULL,
    boil_time    INT NOT NULL,

    CONSTRAINT pasta_pk PRIMARY KEY (product_id),
    CONSTRAINT products_fk FOREIGN KEY (product_id) REFERENCES products (product_id),
    CONSTRAINT nonnegative_netto_weight CHECK (netto_weight >= 0),
    CONSTRAINT nonnegative_boil_time CHECK (boil_time >= 0),
    CONSTRAINT nonnegative_energy_value CHECK (energy_value >= 0)
);

CREATE TABLE desserts
(
    product_id   INT         NOT NULL,
    name         VARCHAR(45) NOT NULL,
    dairy_free   BOOLEAN     NOT NULL,
    netto_weight INT         NOT NULL,
    energy_value INT         NOT NULL,

    CONSTRAINT desserts_pk PRIMARY KEY (product_id),
    CONSTRAINT products_fk FOREIGN KEY (product_id) REFERENCES products (product_id),
    CONSTRAINT nonnegative_netto_weight CHECK (netto_weight >= 0),
    CONSTRAINT nonnegative_energy_value CHECK (energy_value >= 0)
);

CREATE TABLE storage
(
    product_id INT NOT NULL,
    quantity   INT NOT NULL,

    CONSTRAINT storage_pk PRIMARY KEY (product_id),
    CONSTRAINT products_fk FOREIGN KEY (product_id) REFERENCES products (product_id),
    CONSTRAINT nonnegative_quantity CHECK (quantity >= 0)
);

CREATE TABLE suppliers
(
    supplier_id INT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(45)  NOT NULL,
    NIP         VARCHAR(16)  NOT NULL,
    country     VARCHAR(45)  NOT NULL,
    city        VARCHAR(45)  NOT NULL,
    street      VARCHAR(45)  NOT NULL,
    postal_code VARCHAR(16)  NOT NULL,
    email       VARCHAR(120) NOT NULL,

    CONSTRAINT suppliers_pk PRIMARY KEY (supplier_id)
);


CREATE TABLE supplies
(
    supply_id   INT  NOT NULL AUTO_INCREMENT,
    supplier_id INT  NOT NULL,
    date        DATE NOT NULL,
    payment     ENUM ('bank_transfer', 'on_delivery') NOT NULL,

    CONSTRAINT supplies_pk PRIMARY KEY (supply_id),
    CONSTRAINT suppliers_fk FOREIGN KEY (supplier_id) REFERENCES suppliers (supplier_id)
);

CREATE TABLE supplies_products
(
    supply_id  INT NOT NULL,
    product_id INT NOT NULL,
    quantity   INT NOT NULL,

    CONSTRAINT supplies_products_pk PRIMARY KEY (supply_id, product_id),
    CONSTRAINT supplies_fk FOREIGN KEY (supply_id) REFERENCES supplies (supply_id),
    CONSTRAINT products_fk FOREIGN KEY (product_id) REFERENCES products (product_id),
    CONSTRAINT nonnegative_quantity CHECK (quantity >= 0)
);

CREATE TABLE users
(
    user_id  INT         NOT NULL AUTO_INCREMENT,
    login    VARCHAR(45) NOT NULL,
    password VARCHAR(20) NOT NULL,
    type     ENUM ('sales_man', 'admin', 'store_keeper', 'store_manager') NOT NULL,

    CONSTRAINT users_pk PRIMARY KEY (user_id)
);
