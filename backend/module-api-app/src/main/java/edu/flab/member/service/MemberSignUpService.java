package edu.flab.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.exception.BusinessException;
import edu.flab.member.domain.GameAccount;
import edu.flab.member.domain.GamePosition;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.member.repository.GameAccountMapper;
import edu.flab.member.repository.MemberMapper;
import edu.flab.web.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberSignUpService {
	private final MemberMapper memberMapper;
	private final GameAccountMapper gameAccountMapper;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Member signUp(MemberSignUpDto dto) {
		if (isAlreadyExist(dto.getEmail(), dto.getGameLoginId())) {
			throw new BusinessException(ErrorCode.DUPLICATE_ACCOUNT);
		}

		Member member = Member.builder()
			.email(dto.getEmail())
			.password(encryptPassword(dto.getPassword()))
			.build();

		GameAccount gameAccount = GameAccount.builder()
			.memberId(member.getId())
			.lolLoginId(dto.getGameLoginId())
			.position(GamePosition.of(dto.getPosition()))
			.build();

		memberMapper.save(member);
		member.setGameAccount(gameAccount);
		gameAccountMapper.save(gameAccount);

		return member;
	}

	private boolean isAlreadyExist(String email, String gameLoginId) {
		return gameAccountMapper.findByLoginId(gameLoginId).isPresent() || memberMapper.findActiveMemberByEmail(email)
			.isPresent();
	}

	private String encryptPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
