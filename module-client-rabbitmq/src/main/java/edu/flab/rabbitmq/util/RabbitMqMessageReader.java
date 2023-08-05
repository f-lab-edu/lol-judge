package edu.flab.rabbitmq.util;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqMessageReader {

	private final ObjectMapper objectMapper;

	public <T> Object readValue(Message message, Class<T> clazz) {
		try {
			return objectMapper.readValue(message.getBody(), clazz);
		} catch (IOException e) {
			log.warn("RabbitMq 메시지 데이터를 바인딩 하는데 실패했습니다. <message = {}>", message, e);
			throw new RuntimeException(e);
		}
	}
}
