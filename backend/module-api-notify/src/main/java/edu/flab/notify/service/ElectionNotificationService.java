package edu.flab.notify.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;

import edu.flab.election.domain.Election;
import edu.flab.election.service.ElectionFindService;
import edu.flab.member.domain.Member;
import edu.flab.member.repository.MemberJpaRepository;
import edu.flab.notification.domain.Notification;
import edu.flab.notification.repository.NotificationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElectionNotificationService {
	private final ElectionFindService electionFindService;
	private final MemberJpaRepository memberJpaRepository;
	private final NotificationJpaRepository notificationJpaRepository;

	public void notifyToCandidates(Long electionId, String electionUrl) {
		Election election = electionFindService.findElection(electionId);

		Member member = election.getMember();

		if (memberJpaRepository.existsById(member.getId())) {
			log.warn("재판 알림을 받을 대상자가 존재하지 않습니다 election = {}, member= {}", election, member);
			return;
		}

		Notification notification = Notification.builder()
			.createdAt(OffsetDateTime.now())
			.contents(electionUrl)
			.build();

		notification.setMember(member);

		notificationJpaRepository.save(notification);
	}
}
