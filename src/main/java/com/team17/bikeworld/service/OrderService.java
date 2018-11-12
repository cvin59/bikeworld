package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.controller.AbstractController;
import com.team17.bikeworld.entity.*;
import com.team17.bikeworld.model.OrderModel;
import com.team17.bikeworld.model.ProductRatingModel;
import com.team17.bikeworld.repositories.OrderRepository;
import com.team17.bikeworld.repositories.ProductRatingRepository;
import com.team17.bikeworld.transformer.OrderTransformer;
import com.team17.bikeworld.transformer.ProductRatingTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderTransformer orderTransformer;

    public Order createOrder(OrderModel orderModel) {
        Order order = new Order();
        orderModel.setId(0);
        orderModel.setStatusId(CoreConstant.STATUS_ORDER_PENDING);
        Date date = new Date();
        date.getTime();
        orderModel.setOrderDate(date);

        order = orderTransformer.OrderModelToEntity(orderModel, order);
        return orderRepository.save(order);
    }

    public Page<Order> getOrderBySeller(String sellerUsername, Pageable pageable) {
        Account seller = new Account();
        seller.setUsername(sellerUsername);
        Page<Order> orders = orderRepository.findBySellerUsername(seller, pageable);
        return orders;
    }

    public Page<Order> getOrderByBuyer(String buyerUsername, Pageable pageable) {
        Account buyer = new Account();
        buyer.setUsername(buyerUsername);
        Page<Order> orders = orderRepository.findByBuyerUsername(buyer, pageable);
        return orders;
    }

    public List<Order> getByProduct(int productId) {
        Product product = new Product();
        product.setId(productId);
        return orderRepository.findByProductId(product);
    }

    public Order changeStatus(int orderId, int statusId) {

        Order order = orderRepository.getOne(orderId);

        OrderStatus status = new OrderStatus();
        status.setId(statusId);
        order.setOrderStatusid(status);

        order = orderRepository.save(order);
        return order;
    }

    public boolean checkRatingRight(int productId, String rater) {
        Product product = new Product();
        product.setId(productId);

        Account account = new Account();
        account.setUsername(rater);

        if (orderRepository.getByProductIdAndBuyerUsername(product, account).size()>0) {
            return true;
        }
        return false;
    }


}
