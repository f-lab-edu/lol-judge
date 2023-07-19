package edu.flab.global.filter;

import static edu.flab.global.config.LoginConstant.*;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		ServletException,
		IOException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = httpRequest.getSession(false);
		if (session == null || session.getAttribute(LOGIN_SESSION_ATTRIBUTE) == null) {
			log.debug("로그인 회원만 사용 가능한 컨텐츠입니다");
			return;
		}

		chain.doFilter(request, response);
	}
}
