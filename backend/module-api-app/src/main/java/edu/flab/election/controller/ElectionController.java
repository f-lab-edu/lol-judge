package edu.flab.election.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.flab.election.dto.ElectionDetailResponseDto;
import edu.flab.election.dto.ElectionEditRequestDto;
import edu.flab.election.dto.ElectionRegisterRequestDto;
import edu.flab.election.dto.ElectionRegisterResponseDto;
import edu.flab.election.service.ElectionDetailService;
import edu.flab.election.service.ElectionEditService;
import edu.flab.election.service.ElectionRegisterService;
import edu.flab.member.domain.Member;
import edu.flab.web.annotation.Login;
import edu.flab.web.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ElectionController {

	private final ElectionRegisterService electionRegisterService;
	private final ElectionDetailService electionDetailService;
	private final ElectionEditService electionEditService;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/elections")
	public SuccessResponse<ElectionRegisterResponseDto> addElection(@Login Member member,
		@RequestBody @Valid ElectionRegisterRequestDto dto) {
		ElectionRegisterResponseDto response = electionRegisterService.register(member.getEmail(), dto);
		return SuccessResponse.of(response);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/elections/{electionId}")
	public SuccessResponse<ElectionDetailResponseDto> findElectionDetail(@PathVariable Long electionId) {
		ElectionDetailResponseDto electionDetail = electionDetailService.findElectionDetail(electionId);
		return SuccessResponse.of(electionDetail);
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/elections")
	public SuccessResponse<Void> updateElection(@RequestBody @Valid ElectionEditRequestDto dto) {
		electionEditService.update(dto);
		return SuccessResponse.ok();
	}
}
