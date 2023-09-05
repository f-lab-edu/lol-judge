package edu.flab.member.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.flab.exception.AuthenticationException;
import edu.flab.member.domain.GameAccount;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberLoginRequestDto;
import edu.flab.member.dto.MemberLoginResponseDto;
import edu.flab.member.repository.MemberMapper;

@ExtendWith(MockitoExtension.class)
class MemberLoginServiceTest {

	@Mock
	private MemberMapper memberMapper;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private MemberLoginService sut;

	@Test
	void 올바른_비밀번호를_입력하면_로그인에_성공한다() {
		// given
		Member member = Member.builder().email("example@example.com").password("1234").build();
		GameAccount gameAccount = GameAccount.builder().lolLoginId("admin123").build();
		member.setGameAccount(gameAccount);

		MemberLoginRequestDto dto = MemberLoginRequestDto.builder()
			.email(member.getEmail())
			.password(member.getPassword())
			.build();
		MockHttpServletRequest request = new MockHttpServletRequest();

		// when
		Mockito.when(memberMapper.findActiveMemberByEmail(dto.getEmail())).thenReturn(Optional.of(member));
		Mockito.when(passwordEncoder.matches(dto.getPassword(), member.getPassword())).thenReturn(true);

		// then
		MemberLoginResponseDto memberLoginResponseDto = sut.login(request, dto);
		assertThat(memberLoginResponseDto.getLolLoginId()).isEqualTo(gameAccount.getLolLoginId());
	}

	@Test
	void 틀린_비밀번호를_입력하면_로그인에_실패한다() {
		// given
		Member member = Member.builder().email("example@example.com").password("1234").build();
		MemberLoginRequestDto dto = MemberLoginRequestDto.builder()
			.email(member.getEmail())
			.password(member.getPassword())
			.build();
		MockHttpServletRequest request = new MockHttpServletRequest();

		// when
		Mockito.when(memberMapper.findActiveMemberByEmail(dto.getEmail())).thenReturn(Optional.of(member));
		Mockito.when(passwordEncoder.matches(dto.getPassword(), member.getPassword())).thenReturn(false);

		// then
		assertThatThrownBy(() -> sut.login(request, dto)).isInstanceOf(AuthenticationException.class);
	}
}
