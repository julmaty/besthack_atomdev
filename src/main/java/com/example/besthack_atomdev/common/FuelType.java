package com.example.besthack_atomdev.common;

public enum FuelType {
    AI_92(2001, "АИ-92"),
    AI_95(2002, "АИ-95"),
    AI_92_ECTO(2003, "АИ-92 Экто"),
    AI_95_ECTO(2004, "АИ-95 Экто"),
    DT(2005, "ДТ");

    private final int code; // Уникальный числовой код топлива
    private final String description; // Описание топлива

    // Конструктор
    FuelType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    // Геттеры
    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    // Метод для получения типа топлива по коду
    public static FuelType fromCode(int code) {
        for (FuelType fuelType : values()) {
            if (fuelType.getCode() == code) {
                return fuelType;
            }
        }
        throw new IllegalArgumentException("Неизвестный код топлива: " + code);
    }

    // Переопределение метода toString для удобства вывода
    @Override
    public String toString() {
        return description + " (Код: " + code + ")";
    }
}
