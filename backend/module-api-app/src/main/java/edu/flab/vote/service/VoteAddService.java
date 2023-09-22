package edu.flab.vote.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.election.config.VoteRule;
import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Vote;
import edu.flab.election.repository.VoteJpaRepository;
import edu.flab.election.service.CandidateFindService;
import edu.flab.election.service.VoteFindService;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberJudgePointCalcDto;
import edu.flab.member.service.MemberJudgePointUpdateService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteAddService {

	private final VoteFindService voteFindService;
	private final VoteJpaRepository voteJpaRepository;
	private final CandidateFindService candidateFindService;
	private final MemberJudgePointUpdateService memberJudgePointUpdateService;

	@Transactional
	public void add(Member voter, Long candidateId) {

		Candidate candidate = candidateFindService.findById(candidateId);

		validate(voter, candidate);

		memberJudgePointUpdateService.minusJudgePoint(new MemberJudgePointCalcDto(voter.getId(), VoteRule.FEE));

		Vote vote = new Vote();
		vote.setMember(voter);
		vote.setCandidate(candidate);

		voteJpaRepository.save(vote);
	}

	private void validate(Member member, Candidate candidate) {
		if (member.getId().equals(candidate.getMember().getId())) {
			throw new IllegalStateException(
				"재판 고소인/피고인은 투표를 할 수 없습니다 "
					+ "<회원 번호 = " + member.getId() + ">"
					+ "<재판 번호 = " + candidate.getElection().getId() + ">"
					+ "<후보자 명단 = " + candidate.getElection().getCandidates() + ">"
					+ "<투표 대상 번호 = " + candidate.getId() + ">"
					+ "<투표 대상 회원번호 = " + candidate.getMember().getId() + ">");
		}

		if (member.getJudgePoint() < VoteRule.FEE) {
			throw new IllegalStateException(
				"회원의 포인트가 참가 비용보다 적습니다 "
					+ "<이메일 = " + member.getEmail() + ">"
					+ "<참여 비용 = " + VoteRule.FEE + ">"
					+ "<보유 포인트 = " + member.getJudgePoint() + ">");
		}

		if (voteFindService.hasVotedBefore(member.getId(), candidate.getId())) {
			throw new IllegalStateException("이미 투표에 참여하였습니다");
		}
	}
}
