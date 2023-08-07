package edu.flab.vote.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.election.domain.Election;
import edu.flab.election.domain.ElectionStatus;
import edu.flab.election.dto.ElectionFindRequestDto;
import edu.flab.election.service.ElectionFindService;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberJudgePointCalcDto;
import edu.flab.member.service.MemberJudgePointUpdateService;
import edu.flab.vote.domain.Vote;
import edu.flab.vote.dto.VoteAddRequestDto;
import edu.flab.vote.repository.VoteMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteAddService {

	private final static int FEE = 10;

	private final VoteFindService voteFindService;
	private final VoteMapper voteMapper;
	private final ElectionFindService electionFindService;
	private final MemberJudgePointUpdateService memberJudgePointUpdateService;

	@Transactional
	public void add(Member member, VoteAddRequestDto dto) {
		validate(member, dto);

		memberJudgePointUpdateService.minusJudgePoint(new MemberJudgePointCalcDto(member.getId(), FEE));

		Vote vote = Vote.builder()
			.candidateId(dto.getCandidateId())
			.electionId(dto.getElectionId())
			.build();

		voteMapper.save(vote);
	}

	public void validate(Member member, VoteAddRequestDto dto) {
		Election election = electionFindService.findElection(
			new ElectionFindRequestDto(dto.getElectionId(), ElectionStatus.IN_PROGRESS));

		if (election.getCandidates().stream().noneMatch(c -> c.getId().equals(dto.getCandidateId()))) {
			throw new IllegalArgumentException(
				"재판에 속하는 후보가 아닙니다 "
					+ "<재판 번호 = " + dto.getElectionId() + ">"
					+ "<후보자 명단 = " + election.getCandidates() + ">"
					+ "<투표 후보자번호 = " + dto.getCandidateId() + ">");
		}

		if (election.getCandidates().stream().anyMatch(c -> c.getId().equals(member.getId()))) {
			throw new IllegalStateException(
				"재판 고소인/피고인은 투표를 할 수 없습니다 "
					+ "<회원 번호 = " + member.getId() + ">"
					+ "<재판 번호 = " + dto.getElectionId() + ">"
					+ "<후보자 명단 = " + election.getCandidates() + ">"
					+ "<투표 후보자번호 = " + dto.getCandidateId() + ">");
		}

		if (member.getJudgePoint() < FEE) {
			throw new IllegalStateException(
				"회원의 포인트가 참가 비용보다 적습니다 "
					+ "<이메일 = " + member.getEmail() + ">"
					+ "<참여 비용 = " + FEE + ">"
					+ "<보유 포인트 = " + member.getJudgePoint() + ">");
		}

		if (voteFindService.hasVotedBefore(member.getId())) {
			throw new IllegalStateException("이미 투표에 참여하였습니다");
		}
	}
}
