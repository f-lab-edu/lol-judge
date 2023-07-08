package edu.flab.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.flab.member.domain.GameAccount;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.member.repository.MemberMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberSignUpService {
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;

	public void signUp(MemberSignUpDto dto) {
		GameAccount gameAccount = GameAccount.builder()
			.lolLoginId(dto.getGameLoginId())
			.nickname(dto.getNickname())
			.rankTier(dto.getRankTier())
			.build();

		Member member = Member.builder()
			.email(dto.getEmail())
			.password(encryptPassword(dto.getPassword()))
			.profileUrl(dto.getProfileUrl())
			.gameAccount(gameAccount)
			.build();

		memberMapper.save(member);
	}

	private String encryptPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
