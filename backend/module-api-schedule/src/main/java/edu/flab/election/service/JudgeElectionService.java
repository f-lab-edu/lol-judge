package edu.flab.election.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.election.config.VoteRule;
import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Election;
import edu.flab.election.domain.ElectionStatus;
import edu.flab.election.domain.Vote;
import edu.flab.member.event.MemberRankScoreUpdateEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JudgeElectionService {

	private final MemberRankScoreUpdateEventPublisher eventPublisher;

	/**
	 *
	 * @param election 판결 대상
	 * 승리측에 투표한 모든 인원들에게 포인트를 지급한다.
	 */
	@Transactional
	public void judge(Election election) {
		election.setStatus(ElectionStatus.FINISHED);

		Candidate winner = election.getWinner();

		winner.getVotes()
			.stream()
			.map(Vote::getMember)
			.forEach(m -> {
				eventPublisher.publishEvent(m);
				m.setJudgePoint(m.getJudgePoint() + VoteRule.FEE * 2);
			});

		log.info("[포인트 지급 완료] 재판 판결에 따라 포인트가 지급되었습니다 <재판 번호 = {}> <승자 = {}>", election.getId(), winner.getId());
	}
}
