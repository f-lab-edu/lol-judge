package edu.flab.election.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import edu.flab.election.domain.Election;
import edu.flab.election.dto.ElectionFindOrderByTotalVotedCountDto;
import edu.flab.election.dto.ElectionFindRequestDto;
import edu.flab.election.repository.ElectionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectionFindService {
	private static final String NOT_FOUND_EXCEPTION_MESSAGE_FORMAT = "재판을 찾을 수 없습니다 < id = %d >";
	private final ElectionMapper electionMapper;

	public Election findPendingElection(Long id) {
		return electionMapper.findPendingElectionById(id)
			.orElseThrow(() -> new NoSuchElementException(String.format(NOT_FOUND_EXCEPTION_MESSAGE_FORMAT, id)));
	}

	public Election findInProgressElection(Long id) {
		return electionMapper.findInProgressElectionById(id)
			.orElseThrow(() -> new NoSuchElementException(String.format(NOT_FOUND_EXCEPTION_MESSAGE_FORMAT, id)));
	}

	public Election findFinishedElection(Long id) {
		return electionMapper.findFinishedElectionById(id)
			.orElseThrow(() -> new NoSuchElementException(String.format(NOT_FOUND_EXCEPTION_MESSAGE_FORMAT, id)));
	}

	public List<Election> findPendingElections(ElectionFindRequestDto dto) {
		return electionMapper.findPendingElections(dto);
	}

	public List<Election> findInProgressElections(ElectionFindRequestDto dto) {
		return electionMapper.findInProgressElections(dto);
	}

	public List<Election> findFinishedElections(ElectionFindRequestDto dto) {
		return electionMapper.findFinishedElections(dto);
	}

	public List<Election> findInProgressElectionsOrderByTotalVotedCount(ElectionFindOrderByTotalVotedCountDto dto) {
		return electionMapper.findInProgressElectionsOrderByTotalVotedCount(dto);
	}

	public List<Election> findFinishedElectionsOrderByTotalVotedCount(ElectionFindOrderByTotalVotedCountDto dto) {
		return electionMapper.findFinishedElectionsOrderByTotalVotedCount(dto);
	}
}
