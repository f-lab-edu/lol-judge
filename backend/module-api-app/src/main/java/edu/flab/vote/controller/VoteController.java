package edu.flab.vote.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.flab.member.dto.MemberLoginResponseDto;
import edu.flab.vote.service.VoteService;
import edu.flab.web.annotation.Login;
import edu.flab.web.response.SuccessResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VoteController {

	private final VoteService voteService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/vote/{candidateId}")
	public SuccessResponse<Void> vote(@Login MemberLoginResponseDto sessionMember, @PathVariable Long candidateId) {
		voteService.vote(sessionMember.getMemberId(), candidateId);
		return SuccessResponse.ok();
	}
}
