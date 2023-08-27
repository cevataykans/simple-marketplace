DROP TABLE IF EXISTS products;

CREATE TABLE products
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    price       REAL         NOT NULL
);

DROP TABLE IF EXISTS orders;

CREATE TABLE orders
(
    id        BIGSERIAL PRIMARY KEY,
    productId BIGSERIAL,
    quantity  INT,
    CONSTRAINT fk_product
        FOREIGN KEY (productId)
            REFERENCES products (id)
)