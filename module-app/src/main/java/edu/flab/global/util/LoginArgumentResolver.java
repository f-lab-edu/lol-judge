package edu.flab.global.util;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import edu.flab.global.annotation.Login;
import edu.flab.global.config.LoginConstant;
import edu.flab.member.domain.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Login.class) && parameter.getParameterType()
			.isAssignableFrom(Member.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		HttpSession session = request.getSession(false);

		if (session == null) {
			return null;
		}

		return session.getAttribute(LoginConstant.LOGIN_SESSION_ATTRIBUTE);
	}
}
