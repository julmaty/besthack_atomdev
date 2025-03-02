package com.example.besthack_atomdev.dto;

import java.time.LocalDate;

public class OrderResponse {
    private LocalDate orderDate; // Дата заказа
    private double volume; // Объем заказа
    private String deliveryType; // Тип доставки на русском языке
    private LotListResponse lot; // Лот в формате LotListResponse

    // Конструктор
    public OrderResponse(LocalDate orderDate, double volume, String deliveryType, LotListResponse lot) {
        this.orderDate = orderDate;
        this.volume = volume;
        this.deliveryType = deliveryType;
        this.lot = lot;
    }

    // Геттеры (сеттеры не нужны, так как объект immutable)
    public LocalDate getOrderDate() {
        return orderDate;
    }

    public double getVolume() {
        return volume;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public LotListResponse getLot() {
        return lot;
    }
}
