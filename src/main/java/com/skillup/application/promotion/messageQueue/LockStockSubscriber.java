package com.skillup.application.promotion.messageQueue;

import com.alibaba.fastjson2.JSON;
import com.skillup.domain.order.OrderDomain;
import com.skillup.domain.promotion.PromotionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RocketMQMessageListener(topic = "${promotion.topic.lock-stock}", consumerGroup = "lock-stock-group")
public class LockStockSubscriber implements RocketMQListener<MessageExt> {

    @Autowired
    PromotionService promotionService;

    @Override
    @Transactional
    public void onMessage(MessageExt messageExt){
        String messageBody = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        OrderDomain orderDomain = JSON.parseObject(messageBody, OrderDomain.class);
        log.info("PromotionApp: received lock-stock message. OrderId: " + orderDomain.getOrderNumber());
        promotionService.lockStock(orderDomain.getPromotionId());
    }
}
