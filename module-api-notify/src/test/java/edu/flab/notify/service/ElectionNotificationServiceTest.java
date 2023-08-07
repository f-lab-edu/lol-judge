package edu.flab.notify.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import edu.flab.election.domain.Election;
import edu.flab.notify.listener.ElectionNotifyListener;
import edu.flab.rabbitmq.config.RabbitMqQueueName;
import edu.flab.rabbitmq.domain.RabbitMqSender;
import edu.flab.rabbitmq.message.RabbitMqMessage;

@Tag("integration")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ElectionNotificationServiceTest {

	@Autowired
	private RabbitMqSender sender;

	@SpyBean
	private ElectionNotifyListener sut;

	@Test
	@DisplayName("메시지 큐에 재판이 생성됐다는 데이터가 삽입되면, 재판 당사자들에게 전송될 알림 정보가 데이터베이스에 저장된다")
	void test1() throws Exception {
		// given
		Election election = Election.builder().build();

		RabbitMqMessage<Election> rabbitMqMessage = new RabbitMqMessage<>(election,
			RabbitMqQueueName.ELECTION_REGISTER);

		// when
		sender.send(rabbitMqMessage);

		Thread.sleep(100);

		// then
		verify(sut, atLeastOnce()).listenRegistration(ArgumentMatchers.any());
	}

	@Test
	@DisplayName("메시지 큐에 재판이 시작됐다는 데이터가 삽입되면, 재판 당사자들에게 전송될 알림 정보가 데이터베이스에 저장된다")
	void test2() throws Exception {
		// given
		Election election = Election.builder().build();

		RabbitMqMessage<Election> rabbitMqMessage = new RabbitMqMessage<>(election,
			RabbitMqQueueName.ELECTION_IN_PROGRESS);

		// when
		sender.send(rabbitMqMessage);

		Thread.sleep(100);

		// then
		verify(sut, atLeastOnce()).listenInProgress(ArgumentMatchers.any());
	}
}