package us.vicentini.integration;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Component
@RequiredArgsConstructor
public class PriorityQueueChannelAppRunner implements ApplicationRunner {

    private final PrinterGateway priorityPrinterGateway;


    @SneakyThrows
    @Override
    public void run(ApplicationArguments args) {
        List<Future<Message<String>>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Message<String> message = MessageBuilder.withPayload("Printing priority payload for " + i)
                    .setHeader("messageNumber", i)
                    .setPriority(i)
                    .build();

            log.info("Sending Message: {}", message);
            futures.add(priorityPrinterGateway.print(message));
        }

        log.info("Reading responses");
        for (Future<Message<String>> future : futures) {
            log.info("Future message: {}", future.get().getPayload());
        }
    }


    public static class CustomMessageComparator implements Comparator<Message<String>> {

        @Override
        public int compare(Message<String> o1, Message<String> o2) {
            boolean oneIsEven = o1.getPayload().charAt(o1.getPayload().length() - 1) % 2 == 0;
            boolean twoIsEven = o2.getPayload().charAt(o2.getPayload().length() - 1) % 2 == 0;
            return Boolean.compare(oneIsEven, twoIsEven);
        }
    }
}
