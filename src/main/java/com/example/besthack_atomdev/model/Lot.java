package com.example.besthack_atomdev.model;

import com.example.besthack_atomdev.common.FuelType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity // Указывает, что класс является сущностью JPA
@Table(name = "lots") // Определяет имя таблицы в базе данных
public class Lot {

    @Id // Первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоинкремент
    private long id; // Номер лота

    @NotNull(message = "Дата лота не может быть null")
    @Column(name = "lot_date", nullable = false) // Определяет столбец в таблице
    private LocalDate lotDate; // Дата лота

    @Min(value = 1, message = "Код КССС НБ должен быть больше 0")
    @Column(name = "kscss_nb_code", nullable = false)
    private int kscssNbCode; // Код КССС НБ

    @Min(value = 1, message = "Код КССС Топлива должен быть больше 0")
    @Column(name = "kscss_fuel_code", nullable = false)
    private int kscssFuelCode; // Код КССС Топлива

    @Min(value = 1, message = "Стартовой вес должен быть больше 0")
    @Column(name = "start_weight", nullable = false)
    private double startWeight; // Стартовой вес (доступный объем лота в литрах)

    @Min(value = 0, message = "Доступный остаток не может быть отрицательным")
    @Column(name = "available_balance", nullable = false)
    private double availableBalance; // Доступный остаток (по умолчанию стартовый вес)

    @NotNull(message = "Статус не может быть null")
    @Column(name = "status", nullable = false)
    private String status; // Статус ("Подтвержден", "Продан", "Неактивен")

    @Min(value = 1, message = "Цена за 1 тонну должна быть больше 0")
    @Column(name = "price_per_ton", nullable = false)
    private double pricePerTon; // Цена за 1 тонну

    @Column(name = "lot_price", nullable = false)
    private double lotPrice; // Цена лота (общая стоимость лота в рублях)

    // Конструктор без аргументов (требуется для JPA)
    public Lot() {
        this.status = "Подтвержден"; // Начальный статус
    }

    // Конструктор с параметрами
    public Lot(LocalDate lotDate, int kscssNbCode, int kscssFuelCode, double startWeight, double pricePerTon) {
        this.lotDate = lotDate;
        this.kscssNbCode = kscssNbCode;
        this.kscssFuelCode = kscssFuelCode;
        this.startWeight = startWeight;
        this.availableBalance = startWeight; // Инициализация доступного остатка
        this.pricePerTon = pricePerTon;
        this.lotPrice = calculateLotPrice(); // Расчет цены лота
        this.status = "Подтвержден"; // Начальный статус
    }

    // Геттеры и сеттеры
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getLotDate() {
        return lotDate;
    }

    public void setLotDate(LocalDate lotDate) {
        this.lotDate = lotDate;
    }

    public int getKscssNbCode() {
        return kscssNbCode;
    }

    public void setKscssNbCode(int kscssNbCode) {
        this.kscssNbCode = kscssNbCode;
    }

    public int getKscssFuelCode() {
        return kscssFuelCode;
    }

    public void setKscssFuelCode(int kscssFuelCode) {
        this.kscssFuelCode = kscssFuelCode;
    }

    public double getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(double startWeight) {
        this.startWeight = startWeight;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPricePerTon() {
        return pricePerTon;
    }

    public void setPricePerTon(double pricePerTon) {
        this.pricePerTon = pricePerTon;
    }

    public double getLotPrice() {
        return lotPrice;
    }

    public void setLotPrice(double lotPrice) {
        this.lotPrice = lotPrice;
    }

    // Метод для получения типа топлива через enum
    public FuelType getFuelType() {
        return FuelType.fromCode(this.kscssFuelCode);
    }

    // Метод для расчета цены лота
    private double calculateLotPrice() {
        return pricePerTon * (availableBalance / 1000); // Перевод из литров в тонны
    }

    // Метод для оформления заказа
    public void placeOrder(double orderVolume) {
        if (orderVolume > availableBalance) {
            throw new IllegalArgumentException("Заказ превышает доступный остаток");
        }
        availableBalance -= orderVolume;
        lotPrice = calculateLotPrice();

        // Обновление статуса
        if (availableBalance == 0) {
            status = "Продан";
        } else if (LocalDate.now().isAfter(lotDate.plusDays(1))) {
            status = "Неактивен";
        }
    }

    // Метод для обновления статуса лота
    public void updateStatus() {
        if (availableBalance == 0) {
            status = "Продан";
        } else if (LocalDate.now().isAfter(lotDate.plusDays(1))) {
            status = "Неактивен";
        }
    }

    // Переопределение метода toString для удобства отладки
    @Override
    public String toString() {
        return "Lot{" +
                "id=" + id +
                ", lotDate=" + lotDate +
                ", kscssNbCode=" + kscssNbCode +
                ", kscssFuelCode=" + kscssFuelCode +
                ", startWeight=" + startWeight +
                ", availableBalance=" + availableBalance +
                ", status='" + status + '\'' +
                ", pricePerTon=" + pricePerTon +
                ", lotPrice=" + lotPrice +
                '}';
    }
}
