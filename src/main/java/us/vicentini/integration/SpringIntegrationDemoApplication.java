package us.vicentini.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@Slf4j
@SpringBootApplication
@ImportResource("classpath:my-integration-context.xml")
public class SpringIntegrationDemoApplication {

	@Autowired
	private DirectChannel inputChannel;
	@Autowired
	private DirectChannel outputChannel;


	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationDemoApplication.class, args);
	}


	@Bean
	ApplicationRunner appRunner() {
		return arg0 -> {
			outputChannel.subscribe(message -> log.info("Output Channel: {}", message.getPayload()));


			Message<String> message = MessageBuilder.withPayload("Hello World for services")
					.setHeader("newHeader", "new header value")
					.build();

			inputChannel.send(message);
		};
	}
}
