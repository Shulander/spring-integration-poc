package us.vicentini.integration.runner.channel;

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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "runner.channel.MessageBridgeAppRunner", havingValue = "true", matchIfMissing = true)
public class MessageBridgeAppRunner implements ApplicationRunner {

    private final PrinterGateway bridgePrinterGateway;


    @SneakyThrows
    @Override
    public void run(ApplicationArguments args) {
        log.info("RUNNING MessageBridgeAppRunner !!!");
        List<Future<Message<String>>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message<String> message = MessageBuilder.withPayload("Printing Message payload for " + i)
                    .setHeader("messageNumber", i)
                    .setPriority(i)
                    .build();

            log.info("Sending Message: {}", message);
            futures.add(bridgePrinterGateway.print(message));
        }
        log.info("Reading responses");
        for (Future<Message<String>> future : futures) {
            log.info("Future message: {}", future.get().getPayload());
        }
        log.info("------------------------------------");
    }
}
