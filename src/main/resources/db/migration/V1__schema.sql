DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;

CREATE TABLE products
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    price       REAL         NOT NULL
);

CREATE TABLE orders
(
    id         BIGSERIAL PRIMARY KEY,
    product_id BIGSERIAL,
    quantity   INT,
    CONSTRAINT fk_product
        FOREIGN KEY (product_id)
            REFERENCES products (id)
)