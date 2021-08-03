package br.com.maida.Bankapi.controller.form;

import br.com.maida.Bankapi.models.Account;
import br.com.maida.Bankapi.models.User;
import br.com.maida.Bankapi.repository.AccountRepository;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AccountForm {

    @NotNull @NotEmpty
    private String number;

    @NotNull @Min(value=0)
    private BigDecimal balance;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Account convert(AccountRepository accountRepository, User loged){
        return new Account(number, balance, loged);
    }
}
