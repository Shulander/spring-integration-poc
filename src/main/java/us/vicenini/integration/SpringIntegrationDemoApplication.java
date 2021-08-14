package us.vicenini.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@SpringBootApplication
@Configuration
@ImportResource("integration-context.xml")
public class SpringIntegrationDemoApplication implements ApplicationRunner {

	@Autowired
	private CustomGateway gateway;


	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationDemoApplication.class, args);
	}


	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		gateway.print("Hello World");
	}


	@Bean
	ApplicationRunner appRunner() {
		return arg0 -> {
			Message<String> message = MessageBuilder.withPayload("Hello World for services")
					.setHeader("newHeader", "new header value")
					.build();

			PrintService service = new PrintService();
			service.print(message);
		};
	}
}
