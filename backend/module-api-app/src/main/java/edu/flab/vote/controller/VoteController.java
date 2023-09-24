package edu.flab.vote.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.flab.member.domain.Member;
import edu.flab.vote.service.VoteService;
import edu.flab.web.annotation.Login;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VoteController {

	private final VoteService voteService;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/api/vote")
	public void vote(@Login Member member, @RequestBody Long candidateId) {
		voteService.vote(member, candidateId);
	}
}
