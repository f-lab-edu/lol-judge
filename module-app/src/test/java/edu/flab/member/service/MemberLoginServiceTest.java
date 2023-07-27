package edu.flab.member.service;

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

import edu.flab.global.exception.AuthenticationException;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberLoginDto;
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
		MemberLoginDto dto = MemberLoginDto.builder().email(member.getEmail()).password(member.getPassword()).build();
		MockHttpServletRequest request = new MockHttpServletRequest();

		// when
		Mockito.when(memberMapper.findActiveMemberByEmail(dto.getEmail())).thenReturn(Optional.of(member));
		Mockito.when(passwordEncoder.matches(dto.getPassword(), member.getPassword())).thenReturn(true);

		// then
		sut.login(request, dto);
	}

	@Test
	void 틀린_비밀번호를_입력하면_로그인에_실패한다() {
		// given
		Member member = Member.builder().email("example@example.com").password("1234").build();
		MemberLoginDto dto = MemberLoginDto.builder().email(member.getEmail()).password(member.getPassword()).build();
		MockHttpServletRequest request = new MockHttpServletRequest();

		// when
		Mockito.when(memberMapper.findActiveMemberByEmail(dto.getEmail())).thenReturn(Optional.of(member));
		Mockito.when(passwordEncoder.matches(dto.getPassword(), member.getPassword())).thenReturn(false);

		// then
		Assertions.assertThatThrownBy(() -> sut.login(request, dto)).isInstanceOf(AuthenticationException.class);
	}
}
