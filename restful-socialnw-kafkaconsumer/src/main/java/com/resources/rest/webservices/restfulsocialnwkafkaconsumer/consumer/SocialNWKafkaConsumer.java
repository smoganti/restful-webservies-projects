package com.resources.rest.webservices.restfulsocialnwkafkaconsumer.consumer;


import com.resources.rest.webservices.restfulsocialnwkafkaconsumer.constants.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class SocialNWKafkaConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(SocialNWKafkaConsumer.class);

    @KafkaListener(topics = Topics.USERS)
    public void userConsumer(@Payload String payload) {
        logger.info("received payload='{}'", payload);
    }

}
