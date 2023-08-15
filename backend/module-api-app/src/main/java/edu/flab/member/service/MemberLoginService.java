package edu.flab.member.service;

import java.util.NoSuchElementException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.flab.exception.AuthenticationException;
import edu.flab.log.ExceptionLogTrace;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberLoginDto;
import edu.flab.member.repository.MemberMapper;
import edu.flab.web.config.LoginConstant;
import edu.flab.web.response.ErrorCode;
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

	@ExceptionLogTrace
	public void login(HttpServletRequest request, MemberLoginDto dto) {
		Member member = validationEmail(dto.getEmail());

		validationPassword(dto.getPassword(), member.getPassword());

		HttpSession session = request.getSession(true);

		session.setAttribute(LoginConstant.LOGIN_SESSION_ATTRIBUTE, member);
	}

	private Member validationEmail(String email) {
		return memberMapper.findActiveMemberByEmail(email).orElseThrow(NoSuchElementException::new);
	}

	private void validationPassword(String rawPassword, String encryptedPassword) {
		if (!passwordEncoder.matches(rawPassword, encryptedPassword)) {
			throw new AuthenticationException(ErrorCode.WRONG_ACCOUNT);
		}
	}
}
