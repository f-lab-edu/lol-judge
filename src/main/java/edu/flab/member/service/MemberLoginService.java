package edu.flab.member.service;

import static edu.flab.global.config.LoginConfiguration.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.flab.global.annotation.ValidationFailLogTrace;
import edu.flab.global.exception.AuthenticationException;
import edu.flab.global.response.ErrorCode;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberLoginDto;
import edu.flab.member.repository.MemberMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberLoginService {
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;

	@ValidationFailLogTrace
	public void login(HttpServletRequest request, MemberLoginDto dto) {
		Member member = validateMemberEmail(dto.getEmail());

		validatePassword(dto.getPassword(), member.getPassword());

		HttpSession session = request.getSession(true);
		session.setAttribute(LOGIN_SESSION_ATTRIBUTE, member);
	}

	public Member validateMemberEmail(String email) {
		Optional<Member> member = memberMapper.findActiveMemberByEmail(email);

		if (member.isEmpty()) {
			throw new NoSuchElementException("회원을 찾을 수 없습니다");
		}

		return member.get();
	}

	private void validatePassword(String rawPassword, String encryptedPassword) {
		if (!passwordEncoder.matches(rawPassword, encryptedPassword)) {
			throw new AuthenticationException(ErrorCode.PASSWORD_NOT_VALID);
		}
	}
}
