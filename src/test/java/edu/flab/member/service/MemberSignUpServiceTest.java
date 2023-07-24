package edu.flab.member.service;

import static edu.flab.member.domain.LolTier.Color.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.flab.member.domain.GameAccount;
import edu.flab.member.domain.LolTier;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.member.repository.GameAccountMapper;
import edu.flab.member.repository.MemberMapper;

@ExtendWith(MockitoExtension.class)
class MemberSignUpServiceTest {

	@Mock
	private MemberMapper memberMapper;
	@Mock
	private GameAccountMapper gameAccountMapper;
	@Mock
	private PasswordEncoder passwordEncoder;
	@InjectMocks
	private MemberSignUpService sut;

	private final LolTier challenger = LolTier.highTier(CHALLENGER, 1000);

	@Test
	@DisplayName("회원가입 서비스는 전달받은 정보를 Member 객체로 변환하고, 비밀번호는 암호화하여 데이터베이스에 저장한다.")
	void 회원가입() {
		// given
		MemberSignUpDto dto = MemberSignUpDto.builder()
			.email("admin@example.com")
			.password("12341234")
			.profileUrl("cloud.naver.com/bucket/example_profile.jpg")
			.gameLoginId("user12345")
			.nickname("admin")
			.lolTier(challenger)
			.build();

		doNothing().when(memberMapper).save(any(Member.class));
		doNothing().when(gameAccountMapper).save(any(GameAccount.class));
		when(passwordEncoder.encode(anyString())).thenReturn("encrypted password");

		// when
		sut.signUp(dto);

		// then
		GameAccount gameAccount = GameAccount.builder()
			.lolLoginId(dto.getGameLoginId())
			.nickname(dto.getNickname())
			.lolTier(dto.getLolTier()).build();

		Member member = Member.builder()
			.email(dto.getEmail())
			.password("encrypted password")
			.profileUrl(dto.getProfileUrl())
			.gameAccount(gameAccount).build();

		verify(memberMapper).save(member);
	}
}
