package com.example.besthack_atomdev.common;

public enum OilBase {
    OIL_BASE_1(1001, "Нефтебаза 1", "Московская область"),
    OIL_BASE_2(1002, "Нефтебаза 2", "Ленинградская область"),
    OIL_BASE_3(1003, "Нефтебаза 3", "Ростовская область"),
    OIL_BASE_4(1004, "Нефтебаза 4", "Краснодарский край"),
    OIL_BASE_5(1005, "Нефтебаза 5", "Свердловская область"),
    OIL_BASE_6(1006, "Нефтебаза 6", "Тюменская область"),
    OIL_BASE_7(1007, "Нефтебаза 7", "Нижегородская область"),
    OIL_BASE_8(1008, "Нефтебаза 8", "Самарская область"),
    OIL_BASE_9(1009, "Нефтебаза 9", "Волгоградская область"),
    OIL_BASE_10(1010, "Нефтебаза 10", "Челябинская область");

    private final int code; // Уникальный числовой код нефтебазы
    private final String name; // Название нефтебазы
    private final String region; // Регион нефтебазы

    // Конструктор
    OilBase(int code, String name, String region) {
        this.code = code;
        this.name = name;
        this.region = region;
    }

    // Геттеры
    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
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
        return name + " (Код: " + code + ", Регион: " + region + ")";
    }
}