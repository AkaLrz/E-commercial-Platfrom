package com.skillup.application.order;

import com.alibaba.fastjson.JSON;
import com.skillup.application.order.messageQueue.MqRepo;
import com.skillup.domain.order.OrderDomain;
import com.skillup.domain.order.OrderService;
import com.skillup.domain.order.util.OrderStatus;
import com.skillup.domain.promotion.PromotionDomain;
import com.skillup.domain.promotion.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class OrderApplication {

    @Autowired
    PromotionService promotionService;

    @Autowired
    OrderService orderService;

    @Autowired
    MqRepo mqRepo;

    @Value("${order.topic.create-order}")
    String createOrderTopic;

    @Value("${promotion.topic.deduct-stock}")
    String deductStockTopic;

    public OrderDomain createBuyNowOrder(OrderDomain orderDomain) {
        // 1. check promotion exist
        PromotionDomain promotionDomain = promotionService.getPromotionById(orderDomain.getPromotionId());
        if (Objects.isNull(promotionDomain)) {
            orderDomain.setOrderStatus(OrderStatus.ITEM_ERROR);
            return orderDomain;
        }
        // 2. lock stock
        //TODO: Idempent
        boolean isLocked = promotionService.lockStock(orderDomain.getPromotionId());
        if (!isLocked) {
            orderDomain.setOrderStatus(OrderStatus.OUT_OF_STOCK);
            return orderDomain;
        }
        mqRepo.sendMessageToTopic(createOrderTopic, JSON.toJSONString(orderDomain));
        return orderDomain;
    }
    public OrderDomain payBuyNowOrder(Long orderNumber, Integer existStatus, Integer expectStatus) {
        OrderDomain orderDomain = orderService.getOrderById(orderNumber);

        if (Objects.isNull(orderDomain)) return null;

        if (!existStatus.equals(OrderStatus.CREATED.code) || orderDomain.getOrderStatus().equals(OrderStatus.PAYED.code)) return orderDomain;

        if (orderDomain.getOrderStatus().equals(OrderStatus.CREATED)) {
            boolean isPayed = thirdPartyPay();
            if(!isPayed) return orderDomain;

            //1. update order to paid
            orderDomain.setOrderStatus(OrderStatus.PAYED);
            orderDomain.setPayTime(LocalDateTime.now());
            orderService.updateOrder(orderDomain);

            //2. deduct promotion stock
            mqRepo.sendMessageToTopic(deductStockTopic, JSON.toJSONString(orderDomain));
            return orderDomain;
        }
        return orderDomain;
    }
    public boolean thirdPartyPay(){
        return true;
    }
}
