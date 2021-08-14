package us.vicentini.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@SpringBootApplication
@ImportResource("classpath:my-integration-context.xml")
public class SpringIntegrationDemoApplication {

	@Autowired
	@Qualifier("messageChannel")
	private DirectChannel channel;

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationDemoApplication.class, args);
	}


	@Bean
	ApplicationRunner appRunner() {
		return arg0 -> {
			channel.subscribe(message -> {
				PrintService service = new PrintService();
				service.print((Message<String>) message);
			});


			Message<String> message = MessageBuilder.withPayload("Hello World for services")
					.setHeader("newHeader", "new header value")
					.build();

			channel.send(message);
		};
	}
}
