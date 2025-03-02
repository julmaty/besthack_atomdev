package com.example.besthack_atomdev.common;

public enum DeliveryType {
    SELF_PICKUP("Самовывоз"),
    DELIVERY("Доставка");

    private final String description;

    // Конструктор
    DeliveryType(String description) {
        this.description = description;
    }

    // Геттер
    public String getDescription() {
        return description;
    }

    // Метод для поиска значения перечисления по описанию
    public static DeliveryType fromDescription(String description) {
        for (DeliveryType type : values()) {
            if (type.getDescription().equalsIgnoreCase(description)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Недопустимый тип доставки: " + description);
    }

    // Переопределение метода toString для удобства вывода
    @Override
    public String toString() {
        return description;
    }
}
