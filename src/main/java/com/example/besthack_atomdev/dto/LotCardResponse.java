package com.example.besthack_atomdev.dto;

import java.time.LocalDate;

public class LotCardResponse {
    private long id;
    private LocalDate lotDate;
    private int oilBaseCode; // Код нефтебазы
    private int fuelTypeCode; // Код топлива
    private double startWeight;
    private double availableBalance;
    private String status;
    private double pricePerTon;
    private double lotPrice;
    private double availableBalanceByOilBase; // Новое поле

    // Конструктор
    public LotCardResponse(long id, LocalDate lotDate, int oilBaseCode, int fuelTypeCode,
                           double startWeight, double availableBalance, String status,
                           double pricePerTon, double lotPrice, double availableBalanceByOilBase) {
        this.id = id;
        this.lotDate = lotDate;
        this.oilBaseCode = oilBaseCode;
        this.fuelTypeCode = fuelTypeCode;
        this.startWeight = startWeight;
        this.availableBalance = availableBalance;
        this.status = status;
        this.pricePerTon = pricePerTon;
        this.lotPrice = lotPrice;
        this.availableBalanceByOilBase = availableBalanceByOilBase;
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

    public double getAvailableBalanceByOilBase() {
        return availableBalanceByOilBase;
    }
}
