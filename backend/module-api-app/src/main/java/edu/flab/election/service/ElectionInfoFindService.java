package edu.flab.election.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.flab.election.dto.ElectionInfoDto;
import edu.flab.election.dto.ElectionInfoFindResponseDto;
import edu.flab.election.repository.ElectionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectionInfoFindService {

	private final ElectionJpaRepository electionJpaRepository;

	public ElectionInfoFindResponseDto findWithPaging(int pageNumber, int pageSize) {
		List<ElectionInfoDto> electionInfos = electionJpaRepository.findAll(
				PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "id"))
			.get()
			.map(ElectionInfoDto::new)
			.toList();

		Long entireSize = electionJpaRepository.count();

		return new ElectionInfoFindResponseDto(entireSize, electionInfos);
	}
}
