package br.com.maida.Bankapi.controller.dto;

import br.com.maida.Bankapi.models.Account;

import java.math.BigDecimal;

public class BalanceDTO {

    private String account_number;
    private BigDecimal balance;

    public BalanceDTO(Account account){
        this.account_number = account.getNumber();
        this.balance = account.getBalance();
    }

    public String getAccount_number() {
        return account_number;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
