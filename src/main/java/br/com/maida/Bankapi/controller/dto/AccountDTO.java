package br.com.maida.Bankapi.controller.dto;

import br.com.maida.Bankapi.models.Account;

import java.math.BigDecimal;

public class AccountDTO
{
    private String number;
    private BigDecimal balance;
    private UserDTO user;

    public AccountDTO(Account account){
        this.number = account.getNumber();
        this.balance = account.getBalance();
        this.user = new UserDTO(account.getUser());
    }

    public String getNumber() {
        return number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public UserDTO getUser() {
        return user;
    }
}
