package us.vicentini.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@Slf4j
public class PrintService {

    public void print(String message) {
        log.info(message);
    }


    public Message<String> printMessage(Message<String> message) {
        message.getHeaders().forEach((s, o) -> log.info("'{}': {}", s, o));
        log.info(message.getPayload());

        return MessageBuilder.withPayload("Response Message for messageId: " + message.getHeaders().get("id")).build();
    }
}
