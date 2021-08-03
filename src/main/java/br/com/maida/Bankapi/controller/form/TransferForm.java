package br.com.maida.Bankapi.controller.form;

import br.com.maida.Bankapi.models.Account;
import br.com.maida.Bankapi.models.Transfer;
import br.com.maida.Bankapi.models.User;
import br.com.maida.Bankapi.repository.AccountRepository;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransferForm {

    @NotEmpty @NotNull
    private String source_account_number;
    @NotEmpty @NotNull
    private String destination_account_number;
    @NotEmpty @Min(value = 0)
    private BigDecimal amount;

    public String getSource_account_number() {
        return source_account_number;
    }

    public void setSource_account_number(String source_account_number) {
        this.source_account_number = source_account_number;
    }

    public String getDestination_account_number() {
        return destination_account_number;
    }

    public void setDestination_account_number(String destination_account_number) {
        this.destination_account_number = destination_account_number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public Transfer convert(AccountRepository accountRepository, User authUser){
        Account source_account = accountRepository.findByNumber(source_account_number);
        Account destination_account = accountRepository.findByNumber(destination_account_number);

        return new Transfer(destination_account, source_account, amount);
    }
}
