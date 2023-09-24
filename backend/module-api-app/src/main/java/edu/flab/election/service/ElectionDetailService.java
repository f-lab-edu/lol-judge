package edu.flab.election.service;

import org.springframework.stereotype.Service;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Election;
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
			.writer(election.getMember().getGameAccount().getNickname())
			.title(election.getTitle())
			.youtubeUrl(election.getYoutubeUrl())
			.totalVotedCount(election.getTotalVotedCount())
			.createdAt(election.getCreatedAt())
			.endedAt(election.getEndedAt())
			.opinions(election.getCandidates().stream().map(Candidate::getOpinion).toList())
			.build();
	}
}
