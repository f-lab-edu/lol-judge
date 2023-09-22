package edu.flab.notify.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Election;
import edu.flab.election.service.ElectionFindService;
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

		List<Candidate> candidates = election.getCandidates();

		if (isEmptyCandidates(candidates)) {
			log.warn("재판 알림을 받을 대상자가 존재하지 않습니다 election = {}", election);
			return;
		}

		candidates.forEach(c -> {
			if (!memberJpaRepository.existsById(c.getMember().getId())) {
				log.info("존재하지 않거나 탈퇴한 회원에 대한 메시지 전송 요청입니다 <memberId = {}>", c.getMember().getId());
				return;
			}

			Notification notification = Notification.builder()
				.createdAt(OffsetDateTime.now())
				.contents(electionUrl)
				.build();

			notification.setMember(c.getMember());

			notificationJpaRepository.save(notification);
		});
	}

	private boolean isEmptyCandidates(List<Candidate> candidates) {
		return candidates == null || candidates.isEmpty();
	}
}
