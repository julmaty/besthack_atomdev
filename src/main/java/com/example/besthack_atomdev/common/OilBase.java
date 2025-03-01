package com.example.besthack_atomdev.common;

public enum OilBase {
    OIL_BASE_1(1001, "Нефтебаза 1"),
    OIL_BASE_2(1002, "Нефтебаза 2"),
    OIL_BASE_3(1003, "Нефтебаза 3"),
    OIL_BASE_4(1004, "Нефтебаза 4"),
    OIL_BASE_5(1005, "Нефтебаза 5"),
    OIL_BASE_6(1006, "Нефтебаза 6"),
    OIL_BASE_7(1007, "Нефтебаза 7"),
    OIL_BASE_8(1008, "Нефтебаза 8"),
    OIL_BASE_9(1009, "Нефтебаза 9"),
    OIL_BASE_10(1010, "Нефтебаза 10");

    private final int code; // Уникальный числовой код нефтебазы
    private final String name; // Название нефтебазы

    // Конструктор
    OilBase(int code, String name) {
        this.code = code;
        this.name = name;
    }

    // Геттеры
    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    // Метод для получения нефтебазы по коду
    public static OilBase fromCode(int code) {
        for (OilBase oilBase : values()) {
            if (oilBase.getCode() == code) {
                return oilBase;
            }
        }
        throw new IllegalArgumentException("Неизвестный код нефтебазы: " + code);
    }

    // Переопределение метода toString для удобства вывода
    @Override
    public String toString() {
        return name + " (Код: " + code + ")";
    }
}
