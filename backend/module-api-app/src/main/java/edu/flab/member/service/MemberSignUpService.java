package edu.flab.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.exception.BusinessException;
import edu.flab.member.domain.GameAccount;
import edu.flab.member.domain.GamePosition;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.member.repository.MemberJpaRepository;
import edu.flab.web.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberSignUpService {
	private final MemberJpaRepository memberJpaRepository;
	private final PasswordEncoder passwordEncoder;
	private final RiotApiService riotApiService;

	@Transactional
	public Member signUp(MemberSignUpDto dto) {
		if (memberJpaRepository.existsByEmail(dto.getEmail())) {
			throw new BusinessException(ErrorCode.DUPLICATE_ACCOUNT);
		}

		Member member = Member.builder()
			.email(dto.getEmail())
			.password(encryptPassword(dto.getPassword()))
			.profileUrl(dto.getProfileUrl())
			.build();

		GameAccount gameAccount = GameAccount.builder()
			.lolId(dto.getLolId())
			.nickname(riotApiService.getUserNickName(dto.getLolId()))
			.position(GamePosition.of(dto.getPosition()))
			.build();

		member.setGameAccount(gameAccount);

		return memberJpaRepository.save(member);
	}

	private String encryptPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
