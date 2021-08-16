package us.vicentini.integration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

@Slf4j
public class NumericPrintService {

    public void printMessage(Message<?> message) {
        log.info("Printing the numeric: {}", message.getPayload());
    }
}
