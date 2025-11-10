package com.sashafiesta.finances.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Operation {
    private long id;
    private CategoryType type;
    private long bank_account_id;
    private BigDecimal amount;
    private LocalDateTime date;
    private String description;
    private long category_id;

    public Operation(long id, CategoryType type, long bank_account_id, BigDecimal amount, LocalDateTime date, String description, long category_id) {
        this.id = id;
        this.type = type;
        this.bank_account_id = bank_account_id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category_id = category_id;
    }

    public long getId() {
        return id;
    }

    public CategoryType getType() {
        return type;
    }

    public long getBankAccountId() {
        return bank_account_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public long getCategoryId() {
        return category_id;
    }


    public void setType(CategoryType type) {
        this.type = type;
    }

    public void setBankId(long bank_account_id) {
        this.bank_account_id = bank_account_id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryId(long category_id) {
        this.category_id = category_id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        return this.id == ((Operation)object).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return id + ": " + (type == CategoryType.INCOME ? "Зачисление" : "Списание") + "#" + id + " на сумму " + amount + "(" + description + "). Счет #" + bank_account_id + ". Время: " + date;
    }
}
