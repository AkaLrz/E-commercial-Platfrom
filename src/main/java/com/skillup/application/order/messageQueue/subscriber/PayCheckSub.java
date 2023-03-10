package com.skillup.application.order.messageQueue.subscriber;

import com.alibaba.fastjson2.JSON;
import com.skillup.application.order.messageQueue.MqRepo;
import com.skillup.domain.order.OrderDomain;
import com.skillup.domain.order.OrderService;
import com.skillup.domain.order.util.OrderStatus;
import com.skillup.domain.stockCache.StockCacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Component
@RocketMQMessageListener(topic = "${order.topic.pay-check}", consumerGroup = "${order.topic.pay-check-group}")
public class PayCheckSub implements RocketMQListener<MessageExt> {
    @Autowired
    OrderService orderService;

    @Autowired
    MqRepo mqRepo;

    @Value("${promotion.topic.revert-stock}")
    String revertStockTopic;

    @Autowired
    StockCacheService stockCacheService;

    @Override
    public void onMessage(MessageExt messageExt) {
        //parse to String, if needed, parse to Object by FastJson2
        String messageBody = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        OrderDomain orderDomain = JSON.parseObject(messageBody, OrderDomain.class);
        log.info("OrderApp: received pay-check message.");

        //1.get newest order info
        Long orderId = orderDomain.getOrderNumber();
        OrderDomain currentOrder = orderService.getOrderById(orderId);
        if (Objects.isNull(currentOrder)) {
            throw new RuntimeException("Order doesn't exists");
        }

        // 2. order status equals created = not pay
        if (currentOrder.getOrderStatus().equals(OrderStatus.CREATED)) {
            //1. update order status to overtime
            currentOrder.setOrderStatus(OrderStatus.OVERTIME);
            orderService.updateOrder(currentOrder);

            //2. revert cache available_stock
            boolean isReverted = stockCacheService.revertStock(currentOrder.getPromotionId());
            if (!isReverted) {
                throw new RuntimeException("Revert cache available stock failed");
            }
            //3. database revert stock(available_stock, lock_Stock)
            mqRepo.sendMessageToTopic("String revertStockTopic;\n", JSON.toJSONString(currentOrder));
            log.info("OrderApp: sent revert-stock message");
        }
        else {
            log.info("OrderId: " + orderDomain.getOrderNumber() + " is already payed");
        }
    }
}
