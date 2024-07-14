CREATE TABLE IF NOT EXISTS customers
(
    customer_id BIGSERIAL    NOT NULL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    nik         VARCHAR(16)  NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    created_by  VARCHAR(255) NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "accounts"
(
    "customer_id"    BIGSERIAL    NOT NULL,
    "account_number" VARCHAR(100) NOT NULL PRIMARY KEY,
    "account_type"   VARCHAR(50)  NOT NULL,
    "branch_address" VARCHAR(255) NOT NULL,
    "created_at"     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    "created_by"     VARCHAR(255) NOT NULL,
    "updated_at"     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    "updated_by"     VARCHAR(255) NOT NULL
);

ALTER TABLE IF EXISTS accounts
    ALTER COLUMN account_number TYPE bigint USING account_number::bigint;