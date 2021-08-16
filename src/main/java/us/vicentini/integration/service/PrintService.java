package us.vicentini.integration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@Slf4j
public class PrintService {

    public Message<?> printMessage(Message<?> message) {
        log.info("{}", message.getPayload());

//        throw new RuntimeException("Error printing message");
        return MessageBuilder.withPayload("Response Message for messageId: " + message.getHeaders().get("id")).build();
    }


    public Message<?> printMessageWithMessageNumber(Message<?> message) {
        log.info("{}", message.getPayload());
        Object messageNumber = message.getHeaders().get("messageNumber");
        return MessageBuilder.withPayload("Response Message for message: #" + messageNumber).build();
    }
}
