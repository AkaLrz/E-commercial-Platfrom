package com.skillup.infrastructure.rocketMq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RocketMqRepoTest {
    @Autowired
    RocketMqRepo rocketMqRepo;

    @Test
    public void sendToTestTopic() {
        rocketMqRepo.sendMessageToTopic("test-topic", "Hello, rocketMQ!");
    }
}