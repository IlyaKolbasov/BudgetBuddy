-- V1__create_exchange_rate.sql

CREATE TABLE exchange_rate (
    id BIGSERIAL PRIMARY KEY,
    symbol VARCHAR(255),
    rate DOUBLE PRECISION,
    date TIMESTAMP WITH TIME ZONE
);