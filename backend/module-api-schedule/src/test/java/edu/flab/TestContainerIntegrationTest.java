package edu.flab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@Tag("integration")
@Testcontainers
@SpringBootTest
public class TestContainerIntegrationTest {
	@Container
	private static final GenericContainer redis = new GenericContainer(
		DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);

	@Container
	private static final RabbitMQContainer rabbitmq = new RabbitMQContainer("rabbitmq:latest").withExposedPorts(5672,
		15672);

	@BeforeEach
	void setUpTestContainer() {
		redis.start();
		rabbitmq.start();
	}

	@DynamicPropertySource
	static void registerProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.rabbitmq.host", rabbitmq::getHost);
		registry.add("spring.rabbitmq.port", rabbitmq::getAmqpPort);
		registry.add("spring.rabbitmq.username", rabbitmq::getAdminUsername);
		registry.add("spring.rabbitmq.password", rabbitmq::getAdminPassword);
	}
}
