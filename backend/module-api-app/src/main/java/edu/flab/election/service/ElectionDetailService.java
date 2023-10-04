package edu.flab.election.service;

import org.springframework.stereotype.Service;

import edu.flab.election.domain.Election;
import edu.flab.election.dto.CandidateDetailDto;
import edu.flab.election.dto.ElectionDetailResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectionDetailService {

	private final ElectionFindService electionFindService;

	public ElectionDetailResponseDto findElectionDetail(Long electionId) {
		Election election = electionFindService.findElection(electionId);

		return ElectionDetailResponseDto.builder()
			.id(electionId)
			.writer(election.getMember().getGameAccount().getSummonerName())
			.title(election.getTitle())
			.youtubeUrl(election.getYoutubeUrl())
			.totalVotedCount(election.getTotalVotedCount())
			.createdAt(election.getCreatedAt())
			.endedAt(election.getEndedAt())
			.candidateDetails(election.getCandidates().stream().map(c -> new CandidateDetailDto(c.getId(), c.getOpinion())).toList())
			.build();
	}
}
