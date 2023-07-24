package edu.flab.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.member.domain.GameAccount;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.member.repository.GameAccountMapper;
import edu.flab.member.repository.MemberMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberSignUpService {
	private final MemberMapper memberMapper;
	private final GameAccountMapper gameAccountMapper;
	private final PasswordEncoder passwordEncoder;


	@Transactional
	public Member signUp(MemberSignUpDto dto) {
		Member member = Member.builder()
			.email(dto.getEmail())
			.password(encryptPassword(dto.getPassword()))
			.profileUrl(dto.getProfileUrl())
			.build();

		GameAccount gameAccount = GameAccount.builder()
			.memberId(member.getId())
			.lolLoginId(dto.getGameLoginId())
			.nickname(dto.getNickname())
			.lolTier(dto.getLolTier())
			.build();

		memberMapper.save(member);
		member.setGameAccount(gameAccount);
		gameAccountMapper.save(gameAccount);

		return member;
	}

	private String encryptPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
