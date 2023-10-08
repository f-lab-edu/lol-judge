package edu.flab.member.service;

import static org.assertj.core.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.flab.exception.AuthenticationException;
import edu.flab.member.TestFixture;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberLoginRequestDto;
import edu.flab.member.dto.MemberLoginResponseDto;
import edu.flab.web.config.LoginConstant;
import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
class MemberLoginServiceTest {

	@Mock
	private MemberFindService memberFindService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private MemberLoginService sut;

	@Test
	@DisplayName("이메일 인증을 통과한 회원의 경우, 올바른 비밀번호를 입력하고, 로그인을 시도하면 세션에 로그인 정보가 저장된다")
	void test1() {
		// given
		Member member = TestFixture.getMember();
		member.setAuthenticated(true);

		MockHttpServletRequest request = new MockHttpServletRequest();

		MemberLoginRequestDto dto = MemberLoginRequestDto.builder()
			.email(member.getEmail())
			.password(member.getPassword())
			.build();

		// when
		Mockito.when(memberFindService.findActiveMember(dto.getEmail())).thenReturn(member);
		Mockito.when(passwordEncoder.matches(dto.getPassword(), member.getPassword())).thenReturn(true);

		// then
		MemberLoginResponseDto expect = MemberLoginResponseDto.builder()
			.memberId(member.getId())
			.summonerName(member.getGameAccount().getSummonerName())
			.email(member.getEmail())
			.build();

		MemberLoginResponseDto loginResponseDto = sut.login(request, dto);
		assertThat(loginResponseDto).isEqualTo(expect);
		assertThat(sut.getLoginMember(request)).isEqualTo(expect);
	}

	@Test
	@DisplayName("이메일 인증을 통과하지 않은 회원의 경우, 올바른 비밀번호를 입력하고, 로그인을 시도하면 예외가 발생한다")
	void test2() {
		// given
		Member member = TestFixture.getMember();
		member.setAuthenticated(false);

		MockHttpServletRequest request = new MockHttpServletRequest();

		MemberLoginRequestDto dto = MemberLoginRequestDto.builder()
			.email(member.getEmail())
			.password(member.getPassword())
			.build();

		// when
		Mockito.when(memberFindService.findActiveMember(dto.getEmail())).thenThrow(NoSuchElementException.class);

		// then
		assertThatThrownBy(() -> sut.login(request, dto)).isInstanceOf(NoSuchElementException.class);
	}

	@Test
	@DisplayName("로그아웃시 세션이 만료된다")
	void test3() {
		// given
		Member member = TestFixture.getMember();
		member.setAuthenticated(true);

		MockHttpServletRequest request = new MockHttpServletRequest();

		MemberLoginRequestDto dto = MemberLoginRequestDto.builder()
			.email(member.getEmail())
			.password(member.getPassword())
			.build();

		Mockito.when(memberFindService.findActiveMember(dto.getEmail())).thenReturn(member);
		Mockito.when(passwordEncoder.matches(dto.getPassword(), member.getPassword())).thenReturn(true);

		// when
		sut.login(request, dto);
		sut.logout(request);

		// then
		HttpSession session = request.getSession();
		assertThat(session).isNotNull();
		assertThat(session.getAttribute(LoginConstant.LOGIN_SESSION_ATTRIBUTE)).isNull();
	}

	@Test
	@DisplayName("틀린 비밀번호를 입력하면 로그인에 실패한다")
	void test4() {
		// given
		Member member = TestFixture.getMember();
		member.setAuthenticated(true);

		MockHttpServletRequest request = new MockHttpServletRequest();

		MemberLoginRequestDto dto = MemberLoginRequestDto.builder()
			.email(member.getEmail())
			.password(member.getPassword())
			.build();

		// when
		Mockito.when(memberFindService.findActiveMember(dto.getEmail())).thenReturn(member);
		Mockito.when(passwordEncoder.matches(dto.getPassword(), member.getPassword())).thenReturn(false);

		// then
		assertThatThrownBy(() -> sut.login(request, dto)).isInstanceOf(AuthenticationException.class);
	}
}
