package com.example.besthack_atomdev.service;

import com.example.besthack_atomdev.common.DeliveryType;
import com.example.besthack_atomdev.dto.CreateOrderRequest;
import com.example.besthack_atomdev.dto.LotListResponse;
import com.example.besthack_atomdev.dto.OrderResponse;
import com.example.besthack_atomdev.model.Lot;
import com.example.besthack_atomdev.model.Order;
import com.example.besthack_atomdev.repository.LotRepository;
import com.example.besthack_atomdev.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private LotRepository lotRepository;

    public List<OrderResponse> getAllOrders() {
        // Получаем все заказы из репозитория
        List<Order> orders = orderRepository.findAll();

        // Преобразуем каждый заказ в OrderResponse
        return orders.stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    // Получить заказ по ID
    public Optional<OrderResponse> getOrderById(long id) {
        return orderRepository.findById(id)
                .map(this::mapToOrderResponse);
    }

    // Создать новый заказ
    @Transactional
    public Order createOrder(long clientId, CreateOrderRequest request) {
        // Поиск лота по ID
        Lot lot = lotRepository.findById(request.getLotId())
                .orElseThrow(() -> new IllegalArgumentException("Лот с ID " + request.getLotId() + " не найден"));

        // Проверка доступного баланса
        if (request.getVolume() > lot.getAvailableBalance()) {
            throw new IllegalArgumentException("Заказ превышает доступный остаток лота");
        }

        // Преобразование строки deliveryType в enum DeliveryType
        DeliveryType deliveryTypeEnum;
        try {
            deliveryTypeEnum = DeliveryType.fromDescription(request.getDeliveryType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Недопустимый тип доставки: " + request.getDeliveryType());
        }

        // Уменьшение доступного баланса лота
        lot.setAvailableBalance(lot.getAvailableBalance() - request.getVolume());
        lotRepository.save(lot);

        // Создание заказа
        Order order = new Order();
        order.setClientId(clientId);
        order.setOrderDate(LocalDate.now());
        order.setLot(lot);
        order.setVolume(request.getVolume());
        order.setDeliveryType(deliveryTypeEnum); // Используем преобразованный enum
        return orderRepository.save(order);
    }

    // Обновить заказ
    public Order updateOrder(long id, Order updatedOrder) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            updatedOrder.setId(id); // Сохраняем ID
            return orderRepository.save(updatedOrder);
        }
        return null; // Заказ не найден
    }

    // Удалить заказ
    public boolean deleteOrder(long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            orderRepository.deleteById(id);
            return true;
        }
        return false; // Заказ не найден
    }

    private OrderResponse mapToOrderResponse(Order order) {
        String deliveryType = order.getDeliveryType().getDescription(); // Описание типа доставки
        LotListResponse lotListResponse = mapToLotListResponse(order.getLot()); // Преобразуем лот

        return new OrderResponse(
                order.getOrderDate(),
                order.getVolume(),
                deliveryType,
                lotListResponse
        );
    }

    private LotListResponse mapToLotListResponse(Lot lot) {
        String status = switch (lot.getStatus()) {
            case CONFIRMED -> "Подтвержден";
            case SOLD -> "Продан";
            case INACTIVE -> "Неактивен";
        };

        return new LotListResponse(
                lot.getId(),
                lot.getLotDate(),
                lot.getOilBase().getCode(), // Код нефтебазы
                lot.getFuelType().getCode(), // Код топлива
                lot.getStartWeight(),
                lot.getAvailableBalance(),
                status,
                lot.getPricePerTon(),
                lot.getLotPrice()
        );
    }
}
