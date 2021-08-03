package br.com.maida.Bankapi.controller.form;

import br.com.maida.Bankapi.models.Account;
import br.com.maida.Bankapi.repository.AccountRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BalanceForm {

    @NotNull @NotEmpty
    private String account_number;

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public Account accountRetrieve(AccountRepository accountRepository){
        return accountRepository.findByNumber(account_number);
    }
}
