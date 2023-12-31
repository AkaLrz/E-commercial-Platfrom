package com.skillup.application.promotion.messageQueue;

import com.alibaba.fastjson2.JSON;
import com.skillup.domain.promotion.PromotionService;
import com.skillup.domain.promotionStockLog.util.OperationName;
import com.skillup.domain.promotionStockLog.util.PromotionStockLogDomain;
import com.skillup.domain.promotionStockLog.util.PromotionStockLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
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
    @Transactional
    public void onMessage(MessageExt messageExt) {
        String messageBody = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        OrderDomain orderDomain = JSON.parseObject(messageBody, OrderDomain.class);
        log.info("PromotionApp: received revert-stock message." + orderDomain.getOrderNumber());
        promotionService.revertStock(orderDomain.getPromotionId());

        // idempotent
        PromotionStockLogDomain promotionStockLogDomain = promotionStockLogService.getPromotionDomain(orderDomain.getOrderNumber(), OperationName.REVERT_STOCK.name());
        if (Objects.nonNull(promotionStockLogDomain)) return;

        promotionService.revertStock(orderDomain.getPromotionId());
        promotionStockLogService.createPromotionLog(createLockStockDomain(orderDomain));
    }
    private PromotionStockLogDomain createLockStockDomain(OrderDomain orderDomain){
        return PromotionStockLogDomain.builder()
                .createTimes(LocalDateTime.now())
                .promotionId(orderDomain.getPromotionId())
                .orderNumber(orderDomain.getOrderNumber())
                .operationName(OperationName.REVERT_STOCK)
                .userId(orderDomain.getUserId())
                .build();
    }

}