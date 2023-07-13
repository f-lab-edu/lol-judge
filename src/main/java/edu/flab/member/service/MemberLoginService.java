package edu.flab.member.service;

import static edu.flab.global.config.LoginConfiguration.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.flab.global.exception.AuthenticationException;
import edu.flab.global.response.ErrorCode;
import edu.flab.global.vo.HttpRequestInfo;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberLoginDto;
import edu.flab.member.repository.MemberMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그를 남기며 한 고민
 * 1. 예외가 발생할 때 마다, 로그 코드를 작성하는 건 귀찮다. 특정 동작을 할 때마다 로그를 남기게 할 순 없는가? AOP?
 * 2. 어떤 정보를 남겨야 할 것인가? HttpRequest 정보, 메서드 또 무엇을 남기면 좋을까?
 * 3. 로그 레벨 중 무엇을 선택할 것인가?
 * - Info: 정상적 흐름이며 남겨두면 유용한 정보가 있을 때
 * - Warn: 비정상적 흐름이며 자동복구가 가능할 때
 * - Error: 치명적 오류 발생 + 개발자의 개입이 필요한 상황
 * ===
 * 로그인에 실패했을 때 로깅을 한 이유
 * - 당시 스냅샷을 로깅하면, 실제 아이디/비밀번호가 틀려서 그런건지, 브루트포스 공격인지 등의 유용한 정보를 얻을 수 있을 것 같다.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberLoginService {
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;

	public void login(HttpServletRequest request, MemberLoginDto dto) {
		Optional<Member> member = memberMapper.findActiveMemberByEmail(dto.getEmail());

		validationEmailAndPassword(request, dto, member);

		HttpSession session = request.getSession(true);
		session.setAttribute(LOGIN_SESSION_ATTRIBUTE, member);
	}

	private void validationEmailAndPassword(HttpServletRequest request, MemberLoginDto dto, Optional<Member> member) {
		if (member.isEmpty()) {
			log.info("<Method={MemberLoginService.login}> <HttpRequest={}>", new HttpRequestInfo(request, dto));
			throw new NoSuchElementException();
		} else if (!isValidPassword(dto.getPassword(), member.get().getPassword())) {
			log.info("<Method={MemberLoginService.login}> <HttpRequest={}>", new HttpRequestInfo(request, dto));
			throw new AuthenticationException(ErrorCode.PASSWORD_NOT_VALID);
		}
	}

	private boolean isValidPassword(String rawPassword, String encryptedPassword) {
		return passwordEncoder.matches(rawPassword, encryptedPassword);
	}
}
