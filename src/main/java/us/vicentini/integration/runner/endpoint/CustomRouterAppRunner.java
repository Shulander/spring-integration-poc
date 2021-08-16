package us.vicentini.integration.runner.endpoint;

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
@ConditionalOnProperty(value = "runner.endpoint.CustomRouterAppRunner", havingValue = "true",
        matchIfMissing = true)
public class CustomRouterAppRunner implements ApplicationRunner {

    private final PrinterGateway customRouterGateway;


    @SneakyThrows
    @Override
    public void run(ApplicationArguments args) {
        log.info("RUNNING CustomRouterAppRunner !!!");
        List<Future<Message<String>>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message<?> message = getMessage(i);

            log.info("Sending Message: {}", message);
            futures.add(customRouterGateway.print(message));
        }
        //trigger the default payload
        Message<Long> longPayload = MessageBuilder.withPayload(123L).build();
        futures.add(customRouterGateway.print(longPayload));

        log.info("Reading responses");
        for (Future<Message<String>> future : futures) {
            log.info("Future message: {}", future.get().getPayload());
        }
        log.info("------------------------------------");
    }


    private Message<?> getMessage(int i) {
        MessageBuilder<?> returnValue;
        if (Math.random() > 0.5) {
            returnValue = MessageBuilder.withPayload(i)
                    .setHeader("routeHeader", "int");
        } else {
            returnValue = MessageBuilder.withPayload("Printing Message payload for " + i)
                    .setHeader("routeHeader", "string");
        }
        return returnValue
                .setHeader("messageNumber", i).build();
    }
}
