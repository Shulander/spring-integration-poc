package us.vicentini.integration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@Slf4j
public class PrintService {

    public Message<String> printMessage(Message<String> message) {
        log.info("String payload: {}", message.getPayload());

//        throw new RuntimeException("Error printing message");
        return MessageBuilder.withPayload("Response Message for messageId: " + message.getHeaders().get("id")).build();
    }


    public Message<String> printMessageWithMessageNumber(Message<String> message) {
        log.info(message.getPayload());
        Object messageNumber = message.getHeaders().get("messageNumber");
        return MessageBuilder.withPayload("Response Message for message: #" + messageNumber).build();
    }
}
