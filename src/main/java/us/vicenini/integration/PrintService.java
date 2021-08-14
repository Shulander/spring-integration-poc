package us.vicenini.integration;

import org.springframework.messaging.Message;

public class PrintService {

    public void print(String message) {
        System.out.println(message);
    }


    public void print(Message<String> message) {
        System.out.println(message.getPayload());
    }
}
