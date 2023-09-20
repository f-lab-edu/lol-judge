package edu.flab.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.flab.exception.AuthenticationException;
import edu.flab.log.ExceptionLogTrace;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberLoginRequestDto;
import edu.flab.member.dto.MemberLoginResponseDto;
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
	private final MemberFindService memberFindService;
	private final PasswordEncoder passwordEncoder;

	@ExceptionLogTrace
	public MemberLoginResponseDto login(HttpServletRequest request, MemberLoginRequestDto dto) {
		Member member = memberFindService.findActiveMember(dto.getEmail());
		validatePassword(dto.getPassword(), member.getPassword());
		HttpSession session = request.getSession(true);
		session.setAttribute(LoginConstant.LOGIN_SESSION_ATTRIBUTE, member);
		return new MemberLoginResponseDto(member.getId(), member.getGameAccount().getLolId(), member.getEmail());
	}

	public MemberLoginResponseDto getLoginMember(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}
		Member member = (Member)session.getAttribute(LoginConstant.LOGIN_SESSION_ATTRIBUTE);
		return new MemberLoginResponseDto(member.getId(), member.getGameAccount().getLolId(), member.getEmail());
	}

	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}
	}

	private void validatePassword(String rawPassword, String encryptedPassword) {
		if (!passwordEncoder.matches(rawPassword, encryptedPassword)) {
			throw new AuthenticationException(ErrorCode.WRONG_ACCOUNT);
		}
	}
}
