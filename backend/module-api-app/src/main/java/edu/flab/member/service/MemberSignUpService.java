package edu.flab.member.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.exception.BusinessException;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.member.event.MemberSignUpEvent;
import edu.flab.member.repository.MemberJpaRepository;
import edu.flab.web.response.ErrorCode;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberSignUpService {
	private final MemberJpaRepository memberJpaRepository;
	private final PasswordEncoder passwordEncoder;
	private final ApplicationEventPublisher eventPublisher;

	@Transactional
	public Member signUp(MemberSignUpDto dto) {
		String email = validateEmail(dto.getEmail());

		Member member = Member.builder()
			.email(email)
			.password(encryptPassword(dto.getPassword()))
			.profileUrl(dto.getProfileUrl())
			.build();

		eventPublisher.publishEvent(new MemberSignUpEvent(member, dto.getSummonerName(), dto.getPosition()));

		return memberJpaRepository.save(member);
	}

	private String validateEmail(@Email String email) {
		if (memberJpaRepository.existsByEmail(email)) {
			throw new BusinessException(ErrorCode.DUPLICATE_ACCOUNT);
		}
		return email;
	}

	private String encryptPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
