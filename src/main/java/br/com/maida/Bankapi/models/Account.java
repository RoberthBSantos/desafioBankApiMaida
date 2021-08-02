package br.com.maida.Bankapi.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private BigDecimal balance;
    private LocalDateTime create;

    @ManyToOne
    private User user;

    public Account() {
    }

    public Account(String number, BigDecimal balance, User user) {
        this.number = number;
        this.balance = balance;
        this.user = user;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public LocalDateTime getCreate() {
        return create;
    }
    public void setCreate(LocalDateTime create) {
        this.create = create;
    }
}
