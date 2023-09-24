CREATE TABLE states
(
    id            BIGSERIAL PRIMARY KEY       NOT NULL,
    name          VARCHAR(255)                NOT NULL,
    region        VARCHAR(255),
    public_id     VARCHAR(128)                NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE cities
(
    id            BIGSERIAL PRIMARY KEY       NOT NULL,
    name          VARCHAR(255)                NOT NULL,
    public_id     VARCHAR(128)                NOT NULL,
    state_id      BIGINT REFERENCES states (id),
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified TIMESTAMP WITHOUT TIME ZONE NOT NULL
);
