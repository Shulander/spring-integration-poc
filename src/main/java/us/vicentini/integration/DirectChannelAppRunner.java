package us.vicentini.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DirectChannelAppRunner implements ApplicationRunner {

    private final DirectChannel inputChannel;


    @Override
    public void run(ApplicationArguments args) {
        Message<String> message = MessageBuilder.withPayload("Hello World for services")
                .setHeader("newHeader", "new header value")
                .build();

        log.info("Message Sent: {}", message);
        MessagingTemplate template = new MessagingTemplate();
        Message<?> responseMessage = template.sendAndReceive(inputChannel, message);
        log.info("Response Message: {}", responseMessage.getPayload());
    }
}
