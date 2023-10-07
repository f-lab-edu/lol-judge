package edu.flab.notify.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;

import edu.flab.member.domain.Member;
import edu.flab.notification.domain.Notification;
import edu.flab.notification.repository.NotificationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotifyService {
	private final NotificationJpaRepository notificationJpaRepository;

	public void notify(Member member, String contents) {
		Notification notification = Notification.builder()
			.createdAt(OffsetDateTime.now())
			.contents(contents)
			.build();

		notification.setMember(member);

		notificationJpaRepository.save(notification);
	}
}
