package edu.flab.election.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.flab.election.dto.ElectionRegisterRequestDto;
import edu.flab.election.dto.ElectionRegisterResponseDto;
import edu.flab.election.service.ElectionRegisterService;
import edu.flab.web.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ElectionController {

	private final ElectionRegisterService electionAddService;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/api/election")
	public SuccessResponse<ElectionRegisterResponseDto> addElection(@RequestBody ElectionRegisterRequestDto dto) {
		ElectionRegisterResponseDto response = electionAddService.register(dto);
		return SuccessResponse.of(response);
	}
}
