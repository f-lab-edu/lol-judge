package edu.flab.election.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import edu.flab.election.domain.Election;
import edu.flab.election.domain.ElectionStatus;
import edu.flab.election.dto.ElectionFindRequestDto;
import edu.flab.election.dto.ElectionPagingFindRequestDto;
import edu.flab.election.repository.ElectionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectionFindService {
	private static final String NOT_FOUND_EXCEPTION_MESSAGE_FORMAT = "재판을 찾을 수 없습니다 < id = %d >";
	private final ElectionMapper electionMapper;

	public Election findElection(Long electionId) {
		return electionMapper.findElectionById(electionId).orElseThrow(
			() -> new NoSuchElementException(String.format(NOT_FOUND_EXCEPTION_MESSAGE_FORMAT, electionId)));
	}

	public Election findElection(ElectionFindRequestDto dto) {
		return electionMapper.findElectionByStatusAndId(dto)
			.orElseThrow(
				() -> new NoSuchElementException(String.format(NOT_FOUND_EXCEPTION_MESSAGE_FORMAT, dto.getId())));
	}

	public List<Election> findAllElections(ElectionStatus status) {
		return electionMapper.findAllElectionsByStatus(status);
	}

	public List<Election> findAllElectionsWithPaging(ElectionPagingFindRequestDto dto) {
		return electionMapper.findAllElectionsByStatusWithPaging(dto);
	}

	public List<Election> findLatestElectionWithLimit(ElectionPagingFindRequestDto dto) {
		return electionMapper.findLatestElectionsByStatusWithPaging(dto);
	}

	public List<Election> findAllElectionsOrderByTotalCount(ElectionPagingFindRequestDto dto) {
		return electionMapper.findElectionsOrderByTotalVotedCount(dto);
	}
}
