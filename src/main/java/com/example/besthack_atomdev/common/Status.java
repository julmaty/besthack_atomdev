package com.example.besthack_atomdev.common;

public enum Status {
    CONFIRMED("Подтвержден"),
    SOLD("Продан"),
    INACTIVE("Неактивен");

    private final String description;

    // Конструктор
    Status(String description) {
        this.description = description;
    }

    // Геттер
    public String getDescription() {
        return description;
    }

    // Переопределение метода toString для удобства вывода
    @Override
    public String toString() {
        return description;
    }
}
