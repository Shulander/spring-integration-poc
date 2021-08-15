package us.vicentini.integration.runner;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import us.vicentini.integration.PrinterGateway;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "runner.PubSubChannelAppRunner", havingValue = "true", matchIfMissing = true)
public class PubSubChannelAppRunner implements ApplicationRunner {

    private final PrinterGateway pubSubPrinterGateway;


    @SneakyThrows
    @Override
    public void run(ApplicationArguments args) {
        log.info("RUNNING PubSubChannelAppRunner !!!");
        for (int i = 0; i < 10; i++) {
            Message<String> message = MessageBuilder.withPayload("Printing Message payload for " + i)
                    .setHeader("messageNumber", i)
                    .setPriority(i)
                    .build();

            log.info("Sending Message: {}", message);
            pubSubPrinterGateway.print(message);
        }
        log.info("------------------------------------");
    }
}
