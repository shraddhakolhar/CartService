CREATE TABLE cart_items
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    product_id BIGINT       NOT NULL,
    unit_price DOUBLE NOT NULL,
    quantity   INT          NOT NULL,
    total_price DOUBLE NOT NULL,
    CONSTRAINT pk_cart_items PRIMARY KEY (id)
);

ALTER TABLE cart_items
    ADD CONSTRAINT uc_7b092e720240b8d9b71e068b7 UNIQUE (user_email, product_id);