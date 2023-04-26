package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.repositories.OrderRepository;

import com.example.springsecurityapplication.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class OrderController {
    @Autowired
    private OrderService orderService;

    private final OrderRepository orderRepository;

    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/orders")
    public String viewOrders(Model model) {
        List<Order> orders = orderRepository.findAll(); // orderRepository - ваш репозиторий для работы с заказами
        model.addAttribute("orders", orders);
        return "orders"; // orders - имя представления для отображения списка заказов
    }
    
//    @GetMapping("")
//    public List<Order> getAllOrders() {
//        return orderService.getAllOrders();
//    }

    @PostMapping("/orders/{orderId}/status")
    public String updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        Order order = orderRepository.findById(Math.toIntExact(orderId)).orElseThrow(() -> new RuntimeException("Order not found")); // получаем заказ по идентификатору
        order.setStatus(Status.valueOf(status)); // обновляем статус заказа
        orderRepository.save(order); // сохраняем изменения в базе данных
        return "redirect:/orders/" + orderId; // перенаправляем пользователя на страницу с подробностями заказа
    }

}
