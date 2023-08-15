package edu.flab.confirm.strategy;

import java.util.List;

import org.springframework.context.annotation.Primary;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Election;
import edu.flab.member.service.MemberFindService;
import edu.flab.vote.domain.Vote;
import edu.flab.vote.service.VoteFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Primary
@RequiredArgsConstructor
public class PredicateElectionWinnerByLolTierStrategy implements PredicateElectionWinnerStrategy {

	private final MemberFindService memberFindService;
	private final VoteFindService voteFindService;

	@Override
	public Candidate predicate(Election election) {
		Candidate host = election.getCandidates().get(0);
		Candidate participant = election.getCandidates().get(1);

		List<Vote> votes = voteFindService.findAllVotesByElectionId(election.getId());
		validateVotes(votes);

		long scoreOfHost = calcScore(host.getId(), votes);
		long scoreOfParticipant = calcScore(participant.getId(), votes);

		log.info("[재판 승부 계산] <재판 번호 = {}> <원고의 점수 = {}, 피고의 점수 = {}>", election.getId(), scoreOfHost,
			scoreOfParticipant);

		if (scoreOfHost > scoreOfParticipant) {
			return host;
		} else if (scoreOfHost < scoreOfParticipant) {
			return participant;
		}

		throw new IllegalStateException("[재판 승부 계산] 무승부입니다 <electionId = " + election.getId() + ">");
	}

	private long calcScore(Long candidateId, List<Vote> votes) {
		return votes.stream()
			.filter(v -> v.getCandidateId().equals(candidateId))
			.mapToLong(v -> memberFindService.findActiveMember(v.getMemberId())
				.getGameAccount()
				.getLolTier()
				.getColor()
				.getScore())
			.sum();
	}

	private void validateVotes(List<Vote> votes) {
		if (votes == null) {
			throw new IllegalStateException("재판에 대한 투표가 존재하지 않습니다");
		}
	}
}
