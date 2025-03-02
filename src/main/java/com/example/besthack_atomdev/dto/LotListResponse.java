package com.example.besthack_atomdev.dto;

import java.time.LocalDate;

public class LotListResponse {
    private long id;
    private LocalDate lotDate;
    private int oilBaseCode; // Код нефтебазы
    private int fuelTypeCode; // Код топлива
    private double startWeight; // Начальный вес
    private double availableBalance; // Доступный остаток
    private String status; // Статус на русском языке
    private double pricePerTon; // Цена за тонну
    private double lotPrice; // Цена лота

    // Конструктор
    public LotListResponse(long id, LocalDate lotDate, int oilBaseCode, int fuelTypeCode,
                           double startWeight, double availableBalance, String status,
                           double pricePerTon, double lotPrice) {
        this.id = id;
        this.lotDate = lotDate;
        this.oilBaseCode = oilBaseCode;
        this.fuelTypeCode = fuelTypeCode;
        this.startWeight = startWeight;
        this.availableBalance = availableBalance;
        this.status = status;
        this.pricePerTon = pricePerTon;
        this.lotPrice = lotPrice;
    }

    // Геттеры (сеттеры не нужны, так как объект immutable)
    public long getId() {
        return id;
    }

    public LocalDate getLotDate() {
        return lotDate;
    }

    public int getOilBaseCode() {
        return oilBaseCode;
    }

    public int getFuelTypeCode() {
        return fuelTypeCode;
    }

    public double getStartWeight() {
        return startWeight;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public String getStatus() {
        return status;
    }

    public double getPricePerTon() {
        return pricePerTon;
    }

    public double getLotPrice() {
        return lotPrice;
    }
}
