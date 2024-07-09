CREATE TABLE IF NOT EXISTS `loans`
(
    `loan_id`            int          NOT NULL AUTO_INCREMENT,
    `nik`                varchar(16)  NOT NULL,
    `loan_number`        varchar(100) NOT NULL,
    `loan_type`          varchar(100) NOT NULL,
    `total_loan`         int          NOT NULL,
    `amount_paid`        int          NOT NULL,
    `outstanding_amount` int          NOT NULL,
    `created_at`         date         NOT NULL,
    `updated_at`         date DEFAULT NULL,
    PRIMARY KEY (`loan_id`)
);