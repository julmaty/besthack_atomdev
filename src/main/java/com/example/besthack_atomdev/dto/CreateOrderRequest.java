package com.example.besthack_atomdev.dto;

public class CreateOrderRequest {
    private long lotId; // ID лота
    private double volume; // Объем заказа
    private String deliveryType; // Тип доставки (передается как строка)

    // Геттеры и сеттеры
    public long getLotId() {
        return lotId;
    }

    public void setLotId(long lotId) {
        this.lotId = lotId;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }
}
