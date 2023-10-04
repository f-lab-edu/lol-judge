package edu.flab.election.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import edu.flab.election.domain.Election;
import edu.flab.election.repository.ElectionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectionFindService {
	private static final String NOT_FOUND_EXCEPTION_MESSAGE_FORMAT = "재판을 찾을 수 없습니다 < id = %d >";

	private final ElectionJpaRepository electionJpaRepository;

	public Election findElection(Long electionId) {
		return electionJpaRepository.findById(electionId).orElseThrow(
			() -> new NoSuchElementException(String.format(NOT_FOUND_EXCEPTION_MESSAGE_FORMAT, electionId)));
	}
}
