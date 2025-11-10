package com.sashafiesta.finances.domain;

import java.util.Objects;

public class Category {
    private long id;
    private String name;
    private CategoryType type;

    public Category(long id, String name, CategoryType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        return this.id == ((Category)object).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + "#" + id + " (" + (type == CategoryType.INCOME ? "Зачисление" : "Списание") + ")";
    }
}
