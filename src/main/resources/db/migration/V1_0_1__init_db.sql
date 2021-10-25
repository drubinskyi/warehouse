CREATE TABLE articles
(
    id              varchar PRIMARY KEY,
    name            varchar NOT NULL,
    stock           integer NOT NULL,
    CONSTRAINT stock_non_negative check (stock >= 0)
);

CREATE TABLE products
(
    id              varchar PRIMARY KEY,
    name            varchar NOT NULL,
    price           decimal(12,2) NOT NULL
);

CREATE TABLE product_article
(
    product_id      varchar REFERENCES products (id) ON DELETE CASCADE,
    article_id      varchar REFERENCES articles (id),
    amount          integer NOT NULL,
    CONSTRAINT product_article_pkey PRIMARY KEY (product_id, article_id)
);