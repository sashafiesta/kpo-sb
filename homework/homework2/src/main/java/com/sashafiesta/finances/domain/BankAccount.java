package com.sashafiesta.finances.domain;
import java.math.BigDecimal;
import java.util.Objects;

public class BankAccount {
    private long id;
    private String name;
    private BigDecimal balance;

    public BankAccount(long id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void increaseBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void decreaseBalance(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        return this.id == ((BankAccount)object).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + "#" + id + " (" + balance + ")";
    }
}
