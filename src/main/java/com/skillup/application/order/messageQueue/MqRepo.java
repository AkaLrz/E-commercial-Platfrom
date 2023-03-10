package com.skillup.application.order.messageQueue;

public interface MqRepo {
    public void sendMessageToTopic(String topic, String body) ;

    public void sendDelayMessage(String topic, String body, Integer delaySeconds);
}
