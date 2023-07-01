package edu.flab.member.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.flab.global.vo.RankTier;
import edu.flab.member.domain.GameAccount;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.member.repository.MemberMapper;

@ExtendWith(MockitoExtension.class)
class MemberSignUpServiceTest {

	@Mock
	private MemberMapper memberMapper;
	@Mock
	private PasswordEncoder passwordEncoder;
	@InjectMocks
	private MemberSignUpService memberSignUpService;

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
			.rankTier(RankTier.CHALLENGER)
			.build();

		doNothing().when(memberMapper).save(any(Member.class));
		when(passwordEncoder.encode(anyString())).thenReturn("encrypted password");

		// when
		memberSignUpService.signUp(dto);

		// then
		GameAccount gameAccount = GameAccount.builder()
			.loginId(dto.getGameLoginId())
			.nickname(dto.getNickname())
			.rankTier(dto.getRankTier()).build();

		Member member = Member.builder()
			.email(dto.getEmail())
			.password("encrypted password")
			.profileUrl(dto.getProfileUrl())
			.gameAccount(gameAccount).build();

		verify(memberMapper).save(member);
	}
}
