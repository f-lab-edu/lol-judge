package edu.flab.confirm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.confirm.strategy.PredicateElectionWinnerStrategy;
import edu.flab.election.config.VoteRule;
import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Election;
import edu.flab.election.domain.Vote;
import edu.flab.election.service.ElectionFindService;
import edu.flab.election.service.VoteFindService;
import edu.flab.member.dto.MemberJudgePointCalcDto;
import edu.flab.member.service.MemberJudgePointUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JudgePointAssignService {
	private final PredicateElectionWinnerStrategy predicateElectionWinnerStrategy;
	private final ElectionFindService electionFindService;
	private final VoteFindService voteFindService;
	private final MemberJudgePointUpdateService memberJudgePointUpdateService;

	@Transactional
	public void assignJudgePoint(Long electionId) {
		Election election = electionFindService.findElection(electionId);

		Candidate winner = calcWinner(election);

		provideJudgePointToWinner(election.getCost(), winner);
		provideJudgePointToParticipant(winner);

		log.info("[포인트 지급 완료] 재판 판결에 따라 포인트가 지급되었습니다 <재판 번호 = {}> <승자 = {}>", electionId, winner.getId());
	}

	public Candidate calcWinner(Election election) {
		return predicateElectionWinnerStrategy.predicate(election);
	}

	public void provideJudgePointToWinner(int cost, Candidate winner) {
		int reward = cost * 2;
		memberJudgePointUpdateService.plusJudgePoint(new MemberJudgePointCalcDto(winner.getId(), reward));
	}

	public void provideJudgePointToParticipant(Candidate winner) {
		int reward = VoteRule.FEE * 2;
		List<Vote> allVotesByCandidateId = voteFindService.findAllVotesByCandidateId(winner.getId());
		allVotesByCandidateId.forEach(v ->
			memberJudgePointUpdateService.plusJudgePoint(new MemberJudgePointCalcDto(v.getMember().getId(), reward)));
	}
}
