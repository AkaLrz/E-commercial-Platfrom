package com.skillup.infrastructure.rocketMq;

import com.skillup.application.order.messageQueue.MqRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.nio.charset.StandardCharsets;

@Repository
@Slf4j
public class RocketMqRepo implements MqRepo {
    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Value("${order.delay-time}")
    Integer delaySeconds;

    public void sendMessageToTopic(String topic, String body) {
        //1. Create rocketMq message
        Message message = new Message(topic, body.getBytes(StandardCharsets.UTF_8));
        //2. send rocketmq message to related topic
        try{
            rocketMQTemplate.getProducer().send(message);
            log.info("-- send message to rocketMq");
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendDelayMessage(String topic, String body) {
        Message message = new Message(topic, body.getBytes(StandardCharsets.UTF_8));
        message.setDelayTimeLevel(secondsToRocketMqLevel(delaySeconds));
        //2. send rocketmq message to related topic
        try{
            rocketMQTemplate.getProducer().send(message);
            log.info("-- send message to rocketMq");
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Integer secondsToRocketMqLevel(Integer seconds) {
        if(seconds <= 1) return 1;
        if(seconds <= 5) return 2;
        if(seconds <= 10) return 3;
        if (seconds <= 30) return 4;
        if(seconds <= 60) return 5;
        else return 6;
    }
}
