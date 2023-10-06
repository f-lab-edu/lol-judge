package edu.flab.election.schedule;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.mail.domain.Mail;
import edu.flab.mail.repository.MailJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class deleteAuthCodeMailScheduler {
	private final MailJpaRepository mailJpaRepository;

	// 30분마다 실행
	@Scheduled(cron = "0 30 * * * *")
	@Transactional
	public void deleteAuthCodeMail() {
		log.info("[스케줄러 실행] 인증 메일을 삭제합니다");
		List<Mail> mails = mailJpaRepository.findByEndedAt(OffsetDateTime.now());
		mailJpaRepository.deleteAll(mails);
	}
}
