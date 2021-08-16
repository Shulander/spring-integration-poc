package us.vicentini.integration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

@Slf4j
public class DefaultPrintService {

    public void printMessage(Message<?> message) {
        log.info("DEFAULT PRINT SERVICE: {}", message.getPayload());
    }
}
