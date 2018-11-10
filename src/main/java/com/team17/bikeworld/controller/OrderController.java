package com.team17.bikeworld.controller;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Order;
import com.team17.bikeworld.entity.Product;
import com.team17.bikeworld.model.OrderModel;
import com.team17.bikeworld.model.ProductRatingModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.service.OrderService;
import com.team17.bikeworld.service.ProductService;
import com.team17.bikeworld.transformer.OrderTransformer;
import com.team17.bikeworld.viewModel.MultiOrderModel;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderTransformer orderTransformer;

    @PostMapping(CoreConstant.API_ORDER)
    public String createOrder(@RequestParam String orderModelString) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        LOGGER.info(orderModelString);
        try {
            OrderModel newOrder = gson.fromJson(orderModelString, OrderModel.class);
            if (productService.subtractQuantity(newOrder.getProductId(), newOrder.getQuantity())) {
                orderService.createOrder(newOrder);
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @GetMapping(CoreConstant.API_ACCOUNT + "/buy/{seller}")
    public String loadOrderBySeller(@PathVariable String seller,
                                    @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                    @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                    @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                                    @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by(sortBy).ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(page - 1, size, sortable);

        Response<MultiOrderModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        MultiOrderModel data = new MultiOrderModel();

        List<OrderModel> orderModelList = new ArrayList<>();
        Page<Order> orders = orderService.getOrderBySeller(seller, pageable);
        if (orders.getTotalElements() > 0) {
            for (Order order : orders) {
                OrderModel model = new OrderModel();
                orderTransformer.OrderEntityToModel(model, order);
                orderModelList.add(model);
            }
            data.setTotalPage(orders.getTotalPages());
            data.setTotalRecord(orders.getTotalElements());
            data.setCurrentPage(page);
            data.setOrders(orderModelList);

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
        }
        return gson.toJson(response);
    }

    @GetMapping(CoreConstant.API_ACCOUNT + "/sales/{buyer}")
    public String loadOrderByBuyer(@PathVariable String buyer,
                                   @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                   @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                   @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                                   @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by(sortBy).ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(page - 1, size, sortable);

        Response<MultiOrderModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        MultiOrderModel data = new MultiOrderModel();

        List<OrderModel> orderModelList = new ArrayList<>();
        Page<Order> orders = orderService.getOrderByBuyer(buyer, pageable);
        if (orders.getTotalElements() > 0) {
            for (Order order : orders) {
                OrderModel model = new OrderModel();
                orderTransformer.OrderEntityToModel(model, order);
                orderModelList.add(model);
            }
            data.setTotalPage(orders.getTotalPages());
            data.setTotalRecord(orders.getTotalElements());
            data.setCurrentPage(page);
            data.setOrders(orderModelList);

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
        }
        return gson.toJson(response);
    }


    @GetMapping(CoreConstant.API_PRODUCT + "/order")
    public String loadOrderByProduct(@RequestParam int productId) {

        Response<List<OrderModel>> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            List<Order> orderList = orderService.getByProduct(productId);
            if (orderList != null) {
                List<OrderModel> orderModelList = new ArrayList<>();
                for (Order order : orderList) {
                    OrderModel model = new OrderModel();
                    model = orderTransformer.OrderEntityToModel(model, order);
                    orderModelList.add(model);
                }
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, orderModelList);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }

        return gson.toJson(response);
    }

    @PutMapping(CoreConstant.API_ORDER + "/reject")
    public String rejectOrder(@RequestParam("orderId") int orderId) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            Order order = orderService.changeStatus(orderId, CoreConstant.STATUS_ORDER_REJECT);
            productService.addQuantity(order.getProductId(), order.getQuantity());
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }

    @PutMapping(CoreConstant.API_ORDER + "/confirm")
    public String confirmOrder(@RequestParam("orderId") int orderId) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            Order order = orderService.changeStatus(orderId, CoreConstant.STATUS_ORDER_SUCCESS);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
        }
        return gson.toJson(response);
    }
}
