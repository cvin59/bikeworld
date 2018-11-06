package com.team17.bikeworld.transformer;

import com.team17.bikeworld.entity.Account;
import com.team17.bikeworld.entity.Order;
import com.team17.bikeworld.entity.OrderStatus;
import com.team17.bikeworld.entity.Product;
import com.team17.bikeworld.model.OrderModel;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

@Service
public class OrderTransformer {
    public Order OrderModelToEntity(OrderModel model, Order entity) {
        entity.setId(model.getId());
        entity.setAddressDelivery(model.getDeliveryAddr());
        entity.setOrderDate(model.getOrderDate());
        entity.setPhoneContact(model.getPhoneContact());
        entity.setReceiverName(model.getReciever());
        entity.setQuantity(model.getQuantity());
        entity.setTotal(model.getTotal());

        Product product = new Product();
        product.setId(model.getProductId());
        entity.setProductId(product);

        Account seller = new Account();
        seller.setUsername(model.getSeller());
        entity.setSellerUsername(seller);

        Account buyer = new Account();
        buyer.setUsername(model.getBuyer());
        entity.setBuyerUsername(buyer);

        OrderStatus status = new OrderStatus();
        status.setId(model.getStatusId());
        entity.setOrderStatusid(status);

        return entity;
    }

    public OrderModel OrderEntityToModel(OrderModel model, Order order) {
        model.setId(order.getId());
        model.setReciever(order.getReceiverName());
        model.setDeliveryAddr(order.getAddressDelivery());
        model.setOrderDate(order.getOrderDate());
        model.setQuantity(order.getQuantity());
        model.setTotal(order.getTotal());
        model.setPhoneContact(order.getPhoneContact());
        model.setProductId(order.getProductId().getId());
        model.setProductName(order.getProductId().getName());
        model.setSeller(order.getSellerUsername().getUsername());
        model.setBuyer(order.getBuyerUsername().getUsername());
        model.setStatusId(order.getOrderStatusid().getId());
        model.setStatus(order.getOrderStatusid().getName());

        return model;
    }
}
