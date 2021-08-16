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

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "runner.endpoint.AggregatorAppRunner", havingValue = "true",
        matchIfMissing = true)
public class AggregatorAppRunner implements ApplicationRunner {

    private final PrinterGateway aggregatorGateway;


    @SneakyThrows
    @Override
    public void run(ApplicationArguments args) {
        log.info("RUNNING AggregatorAppRunner !!!");
        String[] payloads = {"John Doe", "Jane Doe", "Another One"};
        for (int i = 0; i < payloads.length; i++) {
            Message<?> message =
                    MessageBuilder.withPayload(payloads[i])
                            .setHeader("routeHeader", "string")
                            .setHeader("messageNumber", i).build();

            log.info("Sending Message: {}", message);
            aggregatorGateway.print(message);

        }

        log.info("------------------------------------");
    }

}
