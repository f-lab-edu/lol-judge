package edu.flab.member.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.flab.member.dto.MemberLoginDto;
import edu.flab.member.dto.MemberRankRequestDto;
import edu.flab.member.dto.MemberRankResponseDto;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.member.service.MemberLoginService;
import edu.flab.member.service.MemberRankFindService;
import edu.flab.member.service.MemberSignUpService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MemberController {
	private final MemberSignUpService memberSignUpService;
	private final MemberLoginService memberLoginService;
	private final MemberRankFindService memberRankFindService;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/signUp")
	public void signUp(@RequestBody @Valid MemberSignUpDto dto) {
		memberSignUpService.signUp(dto);
	}

	@PostMapping("/login")
	public void login(HttpServletRequest request, @RequestBody @Valid MemberLoginDto dto) {
		memberLoginService.login(request, dto);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("members/ranking")
	public List<MemberRankResponseDto> getMemberRankingOrderByRankScore(@RequestBody @Valid MemberRankRequestDto dto) {
		return memberRankFindService.getMemberRankingOrderByRankScore(dto);
	}
}
