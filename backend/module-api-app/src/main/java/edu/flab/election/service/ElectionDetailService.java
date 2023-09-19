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
		Candidate host = election.getHost();
		Candidate participant = election.getParticipant();
		return ElectionDetailResponseDto.builder()
			.id(electionId)
			.hostId(host.getMemberId())
			.participantId(participant.getMemberId())
			.cost(election.getCost())
			.youtubeUrl(election.getYoutubeUrl())
			.hostChampion(host.getChampion())
			.hostOpinion(host.getOpinion())
			.participantChampion(participant.getChampion())
			.participantOpinion(participant.getOpinion())
			.build();
	}
}
