package us.vicentini.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.aggregator.AbstractAggregatingMessageGroupProcessor;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.integration.IntegrationMessageHeaderAccessor.CORRELATION_ID;
import static org.springframework.integration.IntegrationMessageHeaderAccessor.SEQUENCE_NUMBER;

@Slf4j
public class CustomAggregator extends AbstractAggregatingMessageGroupProcessor {
    @Override
    protected Object aggregatePayloads(MessageGroup group, Map<String, Object> defaultHeaders) {
        group.getMessages().stream()
                .map(message -> message.getHeaders().get(CORRELATION_ID))
                .forEach(o -> log.info("CORRELATION_ID: '{}'", o));
        return group.getMessages()
                .stream()
                .sorted((o1, o2) -> {
                    Integer s1 = o1.getHeaders().get(SEQUENCE_NUMBER, Integer.class);
                    Integer s2 = o2.getHeaders().get(SEQUENCE_NUMBER, Integer.class);
                    return s2.compareTo(s1);
                })
                .map(Message::getPayload)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
