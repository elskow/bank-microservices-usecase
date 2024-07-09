CREATE TABLE IF NOT EXISTS `customers`
(
    `customer_id` INT AUTO_INCREMENT PRIMARY KEY,
    `name`        VARCHAR(255) NOT NULL,
    `nik`         VARCHAR(16)  NOT NULL,
    `created_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `created_by`  VARCHAR(255) NOT NULL,
    `updated_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by`  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `accounts`
(
    `customer_id`    INT          NOT NULL,
    `account_number` INT AUTO_INCREMENT PRIMARY KEY,
    `account_type`   VARCHAR(50)  NOT NULL,
    `branch_address` VARCHAR(255) NOT NULL,
    `created_at`     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `created_by`     VARCHAR(255) NOT NULL,
    `updated_at`     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_by`     VARCHAR(255) NOT NULL,
);