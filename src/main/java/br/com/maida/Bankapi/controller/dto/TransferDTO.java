package br.com.maida.Bankapi.controller.dto;

import br.com.maida.Bankapi.models.Transfer;
import br.com.maida.Bankapi.models.User;

import java.math.BigDecimal;

public class TransferDTO {
    private BigDecimal amount;
    private String source_account_number;
    private String destination_account_number;
    private UserDTO user_transfer;

    public TransferDTO(Transfer transfer, User user){
        this.amount = transfer.getAmount();
        this.source_account_number = transfer.getSourceAccount().getNumber();
        this.destination_account_number = transfer.getDestinationAccount().getNumber();
        this.user_transfer = new UserDTO(user);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getSource_account_number() {
        return source_account_number;
    }

    public String getDestination_account_number() {
        return destination_account_number;
    }

    public UserDTO getUser_transfer() {
        return user_transfer;
    }
}
