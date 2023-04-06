package com.skillup.infrastructure.rocketMq;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RocketMQMessageListener(topic = "test_topic", consumerGroup = "test_group")
public class TestMessageReceiver implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt messageExt) {
        //parse to String, if needed, parse to Object by FastJson2
        String messageBody = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        System.out.println(messageBody);
    }
}
