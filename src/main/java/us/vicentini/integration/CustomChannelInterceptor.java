package us.vicentini.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
public class CustomChannelInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        Message<?> msg = MessageBuilder.withPayload(message.getPayload() + " !!! Message Intercepted !!!")
                .copyHeaders(message.getHeaders())
                .build();
        return ChannelInterceptor.super.preSend(msg, channel);
    }
}
