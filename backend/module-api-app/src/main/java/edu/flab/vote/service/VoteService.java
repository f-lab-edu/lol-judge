package edu.flab.vote.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.election.domain.Candidate;
import edu.flab.election.service.CandidateFindService;
import edu.flab.election.service.VoteFindService;
import edu.flab.exception.BusinessException;
import edu.flab.member.domain.Member;
import edu.flab.member.service.MemberFindService;
import edu.flab.web.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteService {

	private final MemberFindService memberFindService;
	private final VoteFindService voteFindService;
	private final CandidateFindService candidateFindService;

	@Transactional
	public void vote(Long voterMemberId, Long candidateId) {

		validate(voterMemberId, candidateId);

		Member voter = memberFindService.findActiveMember(voterMemberId);

		Candidate candidate = candidateFindService.findById(candidateId);

		voter.vote(candidate);
	}

	private void validate(Long memberId, Long candidateId) {
		if (voteFindService.hasVotedBefore(memberId, candidateId)) {
			throw new BusinessException(ErrorCode.ALREADY_VOTED);
		}
	}
}
