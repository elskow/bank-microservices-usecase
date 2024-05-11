package com.helmyl.accounts.dto;

import lombok.Data;

@Data
public class AccountDTO {
    private long accountNumber;

    private String accountType;

    private String branchAddress;
}
