package edu.flab.notify.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import edu.flab.election.domain.Election;
import edu.flab.rabbitmq.config.RabbitMqQueueName;
import edu.flab.rabbitmq.domain.RabbitMqSender;
import edu.flab.rabbitmq.message.RabbitMqMessage;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ElectionRegistrationNotificationServiceTest {

	@Autowired
	private RabbitMqSender sender;

	@SpyBean
	private ElectionRegistrationNotificationService sut;

	@Test
	@DisplayName("메시지 큐에 재판이 생성됐다는 데이터가 삽입되면, 재판 당사자들에게 전송될 알림 정보가 데이터베이스에 저장된다")
	void test1() throws Exception {
		// given
		Election election = Election.builder().build();

		RabbitMqMessage<Election> rabbitMqMessage = new RabbitMqMessage<>() {
			@Override
			public Election getObject() {
				return election;
			}

			@Override
			public String getQueueName() {
				return RabbitMqQueueName.ELECTION_REGISTER;
			}
		};

		// when
		sender.send(rabbitMqMessage);

		Thread.sleep(10);

		// then
		verify(sut, atLeastOnce()).saveNotificationFromRabbitMqMessage(any(Message.class));
	}
}
