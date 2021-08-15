package us.vicentini.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@Slf4j
public class UppercasePrintService {

    public Message<String> printMessage(Message<String> message) {
        log.info(message.getPayload().toUpperCase());
        return MessageBuilder.withPayload("Response Message for messageId: " + message.getHeaders().get("id")).build();
    }
}
