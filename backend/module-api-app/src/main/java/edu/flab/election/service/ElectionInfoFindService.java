package edu.flab.election.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.flab.election.domain.Election;
import edu.flab.election.dto.ElectionInfoDto;
import edu.flab.election.dto.ElectionInfoFindResponseDto;
import edu.flab.election.dto.ElectionPagingFindRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectionInfoFindService {

	private final ElectionFindService electionFindService;

	public ElectionInfoFindResponseDto findAllElectionInfoWithPaging(ElectionPagingFindRequestDto dto) {
		List<ElectionInfoDto> electionInfoDtoList = getElections(dto)
			.stream()
			.map(ElectionInfoDto::new)
			.toList();

		long lastId = electionInfoDtoList.get(electionInfoDtoList.size() - 1).getId();

		return new ElectionInfoFindResponseDto(lastId, electionInfoDtoList);
	}

	private List<Election> getElections(ElectionPagingFindRequestDto dto) {
		if (dto.getCountOffset() <= 0) {
			return electionFindService.findLatestElectionWithLimit(dto);
		}
		return electionFindService.findAllElectionsWithPaging(dto);
	}
}
