package com.resources.rest.webservices.socialnetwork.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;



    public void sendMessage(Object obj,String topic) {

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, obj.toString());

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Sent message=[" + obj.toString() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                logger.warn("Unable to send message=["
                        + obj.toString() + "] due to : " + ex.getMessage());
            }
        });
    }
}
