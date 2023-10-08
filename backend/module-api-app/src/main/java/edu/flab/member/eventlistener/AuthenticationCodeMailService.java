package edu.flab.member.eventlistener;

import java.time.OffsetDateTime;
import java.util.NoSuchElementException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import edu.flab.exception.BusinessException;
import edu.flab.mail.domain.Mail;
import edu.flab.mail.repository.MailJpaRepository;
import edu.flab.member.config.MailProperties;
import edu.flab.member.domain.Member;
import edu.flab.member.event.MemberSignUpEvent;
import edu.flab.util.MailAuthCodeGenerator;
import edu.flab.web.config.ServerAddressProperties;
import edu.flab.web.response.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationCodeMailService {
	private final MailProperties mailProperties;
	private final MailJpaRepository mailJpaRepository;
	private final MailAuthCodeGenerator mailAuthCodeGenerator;
	private final JavaMailSender javaMailSender;
	private final ServerAddressProperties backendServer;

	@Transactional
	public void validateAuthenticationCode(String authenticationCode) {
		Mail mail = mailJpaRepository.findByAuthCode(authenticationCode)
			.orElseThrow(() -> new NoSuchElementException("유효한 인증코드가 아닙니다"));

		if (mail.getEndedAt().isBefore(OffsetDateTime.now())) {
			throw new BusinessException(ErrorCode.AUTHENTICATION_CODE_TIME_OUT);
		}

		mail.getMember().setAuthenticated(true);
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void sendAuthenticationCodeEmail(MemberSignUpEvent event) {
		MimeMessage message = javaMailSender.createMimeMessage();
		String authCode = mailAuthCodeGenerator.generateAuthCode();
		String body = "";
		try {
			saveAuthCode(event.member(), authCode);
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
			mimeMessageHelper.setFrom(mailProperties.username());
			mimeMessageHelper.setTo(event.member().getEmail());
			mimeMessageHelper.setSubject("회원가입 인증 메일");
			body += "<h1>회원가입 인증 메일<h1>";
			body +=
				"<p><a href=" + backendServer.fullAddress() + "/auth-agree/" + authCode + ">";
			body += "동의하기";
			body += "</a></p>";
			mimeMessageHelper.setText(body, true);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			log.error("이메일 전송에 실패하였습니다. receiverMail={}", event.member().getEmail());
			throw new RuntimeException(e);
		}
	}

	private void saveAuthCode(Member member, String authCode) {
		Mail mail = new Mail(authCode, OffsetDateTime.now().plusMinutes(30));
		mail.setMember(member);
		mailJpaRepository.save(mail);
	}
}
