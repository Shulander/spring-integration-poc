package us.vicentini.integration;

import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.messaging.Message;

import java.util.List;

public class CustomSplitter extends AbstractMessageSplitter {


    @Override
    protected Object splitMessage(Message<?> message) {
        return List.of(message.getPayload().toString().split(" "));
    }


    public static class HyphenCustomSplitter {
        public List<String> split(Message<?> message) {
            return List.of(message.getPayload().toString().split("-"));
        }
    }
}
