package edu.flab.notify.service;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Election;
import edu.flab.member.repository.MemberMapper;
import edu.flab.notification.domain.Notification;
import edu.flab.notification.repository.NotificationMapper;
import edu.flab.rabbitmq.config.RabbitMqQueueName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectionRegistrationNotificationService {
	@Value("${api.url}")
	private String API_URL;
	private final ObjectMapper objectMapper;
	private final NotificationMapper notificationMapper;
	private final MemberMapper memberMapper;

	@Transactional
	@RabbitListener(queues = RabbitMqQueueName.ELECTION_REGISTER, ackMode = "AUTO")
	public void saveNotificationFromRabbitMqMessage(Message message) {
		Election election = getElectionFromMessage(message);
		String electionModifyUrl = API_URL + "/modify/elections/" + election.getId();

		List<Candidate> candidates = election.getCandidates();

		if (candidates == null) {
			log.info("재판 생성 알림을 받을 대상자가 존재하지 않습니다");
			return;
		}

		candidates.forEach(c -> saveNotification(c, electionModifyUrl));
	}

	public void saveNotification(Candidate candidate, String electionModifyUrl) {
		if (memberMapper.findActiveMemberById(candidate.getMemberId()).isEmpty()) {
			log.info("존재하지 않거나 탈퇴한 회원에 대한 메시지 전송 요청입니다 <memberId = {}>", candidate.getMemberId());
			return;
		}
		Notification notification = Notification.builder()
			.memberId(candidate.getMemberId())
			.createdAt(OffsetDateTime.now())
			.contents("재판이 생성되었습니다. 자신의 의견을 작성하세요. 링크 = " + electionModifyUrl)
			.build();
		notificationMapper.save(notification);
	}

	private Election getElectionFromMessage(Message message) {
		try {
			return objectMapper.readValue(message.getBody(), Election.class);
		} catch (IOException e) {
			log.warn("RabbitMq 메시지 데이터를 바인딩 하는데 실패했습니다. <message = {}>", message, e);
			throw new RuntimeException(e);
		}
	}
}
