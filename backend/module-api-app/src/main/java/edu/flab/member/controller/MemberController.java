package edu.flab.member.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.flab.member.dto.MemberLoginRequestDto;
import edu.flab.member.dto.MemberLoginResponseDto;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.member.eventlistener.AuthenticationCodeMailService;
import edu.flab.member.service.MemberLoginService;
import edu.flab.member.service.MemberSignUpService;
import edu.flab.web.config.ServerAddressProperties;
import edu.flab.web.response.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
public class MemberController {
	private final MemberSignUpService memberSignUpService;
	private final MemberLoginService memberLoginService;
	private final AuthenticationCodeMailService authenticationCodeMailService;
	private final ServerAddressProperties frontendServer;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/signUp")
	public SuccessResponse<Void> signUp(@RequestBody MemberSignUpDto dto) {
		memberSignUpService.signUp(dto);
		return SuccessResponse.ok();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/auth-agree/{authenticationCode}")
	public void agree(HttpServletResponse response, @PathVariable String authenticationCode) throws
		IOException {
		authenticationCodeMailService.validateAuthenticationCode(authenticationCode);
		response.sendRedirect(frontendServer.fullAddress() + "/login");
	}

	@PostMapping("/login")
	public SuccessResponse<MemberLoginResponseDto> login(HttpServletRequest request,
		@RequestBody MemberLoginRequestDto dto) {
		MemberLoginResponseDto loginSuccessDto = memberLoginService.login(request, dto);
		return SuccessResponse.of(loginSuccessDto);
	}

	@GetMapping("/login")
	public SuccessResponse<MemberLoginResponseDto> getLoginMember(HttpServletRequest request) {
		return SuccessResponse.of(memberLoginService.getLoginMember(request));
	}

	@GetMapping("/logout")
	public void logout(HttpServletRequest httpServletRequest) {
		memberLoginService.logout(httpServletRequest);
	}
}
