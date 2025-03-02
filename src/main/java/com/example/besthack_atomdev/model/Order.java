package com.example.besthack_atomdev.model;
import com.example.besthack_atomdev.common.DeliveryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity // Указывает, что класс является сущностью JPA
@Table(name = "orders") // Определяет имя таблицы в базе данных
public class Order {

    @Id // Первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоинкремент
    private long id; // Номер заказа

    @NotNull(message = "Дата заказа не может быть null")
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate; // Дата заказа

    @ManyToOne // Связь "многие к одному" с сущностью Lot
    @JoinColumn(name = "lot_number", referencedColumnName = "id", nullable = false) // Внешний ключ
    private Lot lot; // Ссылка на лот

    @Min(value = 1, message = "Объём должен быть больше 0")
    @Column(name = "volume", nullable = false)
    private double volume; // Объём заказа (в литрах)

    @NotNull(message = "Тип доставки не может быть null")
    @Enumerated(EnumType.STRING) // Сохраняем название enum в базе данных
    @Column(name = "delivery_type", nullable = false)
    private DeliveryType deliveryType;

    @Min(value = 1, message = "Id клиента должен быть больше 0")
    @Column(name = "client_id", nullable = false)
    private long clientId; // Идентификатор клиента B2B

    // Конструктор без аргументов (требуется для JPA)
    public Order() {
    }

    // Конструктор с параметрами
    public Order(LocalDate orderDate, Lot lot, double volume, DeliveryType deliveryType, long clientId) {
        this.orderDate = orderDate;
        this.lot = lot;
        this.volume = volume;
        this.deliveryType = deliveryType;
        this.clientId = clientId;
    }

    // Геттеры и сеттеры
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    // Переопределение метода toString для удобства отладки
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", lot=" + lot +
                ", volume=" + volume +
                ", deliveryType='" + deliveryType + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}