package com.skillup.application.promotion.messageQueue;

import com.alibaba.fastjson2.JSON;
import com.skillup.domain.order.OrderDomain;
import com.skillup.domain.order.util.OrderStatus;
import com.skillup.domain.promotion.PromotionService;
import com.skillup.domain.promotionStockLog.util.PromotionStockLogDomain;
import com.skillup.domain.promotionStockLog.util.PromotionStockLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Component
@RocketMQMessageListener(topic = "${promotion.topic.revert-stock}", consumerGroup = "${promotion.topic.revert-stock-group}")
public class RevertStockSubscriber implements RocketMQListener<MessageExt> {

    @Autowired
    PromotionService promotionService;

    @Autowired
    PromotionStockLogService promotionStockLogService;

    @Override
    public void onMessage(MessageExt messageExt) {
        String messageBody = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        OrderDomain orderDomain = JSON.parseObject(messageBody, OrderDomain.class);
        log.info("PromotionApp: received revert-stock message." + orderDomain.getOrderNumber());
        promotionService.revertStock(orderDomain.getPromotionId());
    }
}