CREATE TABLE IF NOT EXISTS "cards"
(
    "card_id"          SERIAL PRIMARY KEY,
    "nik"              VARCHAR(16)  NOT NULL,
    "card_number"      VARCHAR(100) NOT NULL,
    "card_type"        VARCHAR(100) NOT NULL,
    "total_limit"      INT          NOT NULL,
    "amount_used"      INT          NOT NULL,
    "available_amount" INT          NOT NULL,
    "created_at"       TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    "created_by"       VARCHAR(255) NOT NULL,
    "updated_at"       TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    "updated_by"       VARCHAR(255) NOT NULL
);