package edu.flab.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.flab.member.dto.MemberLoginRequestDto;
import edu.flab.member.dto.MemberLoginResponseDto;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.member.service.MemberLoginService;
import edu.flab.member.service.MemberSignUpService;
import edu.flab.web.response.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {
	private final MemberSignUpService memberSignUpService;
	private final MemberLoginService memberLoginService;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/signUp")
	public SuccessResponse<Void> signUp(@RequestBody @Valid MemberSignUpDto dto) {
		memberSignUpService.signUp(dto);
		return SuccessResponse.ok();
	}

	@PostMapping("/login")
	public SuccessResponse<MemberLoginResponseDto> login(HttpServletRequest request,
		@RequestBody @Valid MemberLoginRequestDto dto) {
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
