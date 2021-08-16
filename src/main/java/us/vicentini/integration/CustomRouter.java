package us.vicentini.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.util.Collection;
import java.util.LinkedList;

@Slf4j
@RequiredArgsConstructor
public class CustomRouter extends AbstractMessageRouter {

    private final MessageChannel intChannel;
    private final MessageChannel stringChannel;
    private final MessageChannel defaultChannel;


    @Override
    protected Collection<MessageChannel> determineTargetChannels(Message<?> message) {
        Collection<MessageChannel> targetChannels = new LinkedList<>();

        if (message.getPayload().getClass().equals(Integer.class)) {
            targetChannels.add(intChannel);
        } else if (message.getPayload().getClass().equals(String.class)) {
            targetChannels.add(stringChannel);
        } else {
            targetChannels.add(defaultChannel);
        }

        return targetChannels;
    }
}
