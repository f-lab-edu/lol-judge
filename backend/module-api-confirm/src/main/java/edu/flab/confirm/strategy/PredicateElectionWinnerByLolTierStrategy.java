package edu.flab.confirm.strategy;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.CandidateStatus;
import edu.flab.election.domain.Election;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Primary
@Component
@RequiredArgsConstructor
public class PredicateElectionWinnerByLolTierStrategy implements PredicateElectionWinnerStrategy {

	@Override
	public Candidate predicate(Election election) {
		Candidate host = election.getCandidate(CandidateStatus.HOST);
		long scoreOfHost = host.calcVotedScore();

		Candidate participant = election.getCandidate(CandidateStatus.PARTICIPANT);
		long scoreOfParticipant = participant.calcVotedScore();

		log.info("[재판 승부 계산] <재판 번호 = {}> <원고의 점수 = {}, 피고의 점수 = {}>", election.getId(), scoreOfHost,
			scoreOfParticipant);

		if (scoreOfHost > scoreOfParticipant) {
			return host;
		} else if (scoreOfHost < scoreOfParticipant) {
			return participant;
		}

		throw new IllegalStateException("[재판 승부 계산] 무승부입니다 <electionId = " + election.getId() + ">");
	}
}
