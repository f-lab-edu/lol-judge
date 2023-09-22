package edu.flab.election.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import edu.flab.election.domain.ElectionStatus;
import edu.flab.election.dto.ElectionInfoDto;
import edu.flab.election.dto.ElectionInfoFindResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectionInfoFindService {

	private final ElectionFindService electionFindService;

	public ElectionInfoFindResponseDto findLatestElections(long pageSize, ElectionStatus status) {
		List<ElectionInfoDto> electionInfoDtoList = electionFindService.findAllByStatus(pageSize, status)
			.stream()
			.map(ElectionInfoDto::new)
			.toList();
		return buildResult(electionInfoDtoList);
	}

	public ElectionInfoFindResponseDto findAllWithPagination(long lastId, long pageSize, ElectionStatus status) {
		List<ElectionInfoDto> electionInfoDtoList = electionFindService.findAllByStatus(lastId, pageSize, status)
			.stream()
			.map(ElectionInfoDto::new)
			.toList();

		return buildResult(electionInfoDtoList);
	}

	public ElectionInfoFindResponseDto buildResult(List<ElectionInfoDto> electionInfoList) {
		long newLastId = electionInfoList.stream()
			.mapToLong(ElectionInfoDto::getId)
			.min()
			.orElseThrow(() -> new NoSuchElementException("재판 정보가 존재하지 않습니다."));

		return new ElectionInfoFindResponseDto(newLastId, electionInfoList);
	}
}
