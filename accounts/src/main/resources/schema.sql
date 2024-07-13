-- Create sequences
CREATE SEQUENCE customers_customer_id_seq;
CREATE SEQUENCE accounts_account_number_seq;

-- Create tables
CREATE TABLE IF NOT EXISTS "customers"
(
    "customer_id" INT          NOT NULL    DEFAULT nextval('customers_customer_id_seq') PRIMARY KEY,
    "name"        VARCHAR(255) NOT NULL,
    "nik"         VARCHAR(16)  NOT NULL,
    "created_at"  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    "created_by"  VARCHAR(255) NOT NULL,
    "updated_at"  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    "updated_by"  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "accounts"
(
    "customer_id"    INT          NOT NULL,
    "account_number" INT          NOT NULL    DEFAULT nextval('accounts_account_number_seq') PRIMARY KEY,
    "account_type"   VARCHAR(50)  NOT NULL,
    "branch_address" VARCHAR(255) NOT NULL,
    "created_at"     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    "created_by"     VARCHAR(255) NOT NULL,
    "updated_at"     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    "updated_by"     VARCHAR(255) NOT NULL
);

-- Alter sequences to set ownership
ALTER SEQUENCE customers_customer_id_seq OWNED BY customers.customer_id;
ALTER SEQUENCE accounts_account_number_seq OWNED BY accounts.account_number;