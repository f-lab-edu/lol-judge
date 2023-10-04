package edu.flab.vote.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.election.config.VoteRule;
import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Vote;
import edu.flab.election.repository.VoteJpaRepository;
import edu.flab.election.service.CandidateFindService;
import edu.flab.election.service.VoteFindService;
import edu.flab.exception.BusinessException;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberJudgePointCalcDto;
import edu.flab.member.service.MemberFindService;
import edu.flab.member.service.MemberJudgePointUpdateService;
import edu.flab.web.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteService {

	private final MemberFindService memberFindService;
	private final VoteFindService voteFindService;
	private final VoteJpaRepository voteJpaRepository;
	private final CandidateFindService candidateFindService;
	private final MemberJudgePointUpdateService memberJudgePointUpdateService;

	@Transactional
	public void vote(Long voterMemberId, Long candidateId) {

		Member voter = memberFindService.findActiveMember(voterMemberId);

		Candidate candidate = candidateFindService.findById(candidateId);

		validate(voter, candidate);

		memberJudgePointUpdateService.minusJudgePoint(new MemberJudgePointCalcDto(voter.getId(), VoteRule.FEE));

		Vote vote = new Vote();
		vote.setMember(voter);
		vote.setCandidate(candidate);

		voteJpaRepository.save(vote);
	}

	private void validate(Member member, Candidate candidate) {
		if (member.getJudgePoint() < VoteRule.FEE) {
			log.info("회원의 포인트가 참가 비용보다 적습니다 "
				+ "<이메일 = " + member.getEmail() + ">"
				+ "<참여 비용 = " + VoteRule.FEE + ">"
				+ "<보유 포인트 = " + member.getJudgePoint() + ">");
			throw new BusinessException(ErrorCode.POINT_NOT_ENOUGH);
		}

		if (voteFindService.hasVotedBefore(member.getId(), candidate.getId())) {
			throw new BusinessException(ErrorCode.ALREADY_VOTED);
		}
	}
}
