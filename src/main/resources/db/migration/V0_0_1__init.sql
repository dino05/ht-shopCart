CREATE TABLE PUBLIC.cart
  (
     id      BIGSERIAL NOT NULL,
     user_id BIGINT NOT NULL,
     CONSTRAINT cart_pk PRIMARY KEY (id)
  );

  CREATE TABLE PUBLIC.cart_item
    (
       id          BIGSERIAL NOT NULL,
       cart_id     BIGINT NOT NULL,
       offer_id    BIGINT NOT NULL,
       quantity    INT NOT NULL DEFAULT 1,
       create_date DATE NOT NULL,
       action_type VARCHAR NOT NULL,
       CONSTRAINT cart_item_pk PRIMARY KEY (id),
       CONSTRAINT cart_item_fk_cart FOREIGN KEY (cart_id) REFERENCES PUBLIC.cart (id)
    );

CREATE TABLE PUBLIC.item_price
  (
     id          BIGSERIAL NOT NULL,
     price       NUMERIC(12, 2) NOT NULL,
     recurrences INT NULL,
     price_type  VARCHAR NOT NULL,
     cart_item_id BIGINT NOT NULL,
     CONSTRAINT item_price_pk PRIMARY KEY (id),
     CONSTRAINT item_price_fk_cart_item FOREIGN KEY (cart_item_id) REFERENCES PUBLIC.cart_item (id)
  );