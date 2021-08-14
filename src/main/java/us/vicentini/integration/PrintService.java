package us.vicentini.integration;

import org.springframework.messaging.Message;

public class PrintService {

    public void print(String message) {
        System.out.println(message);
    }


    public void print(Message<String> message) {
        message.getHeaders().forEach((s, o) -> {
            System.out.printf("'%s': %s%n", s, o);
        });
        System.out.println(message.getPayload());
    }
}
