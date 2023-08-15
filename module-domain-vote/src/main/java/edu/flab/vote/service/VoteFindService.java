package edu.flab.vote.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import edu.flab.vote.domain.Vote;
import edu.flab.vote.repository.VoteMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteFindService {
	private final VoteMapper voteMapper;

	public Vote findVoteById(Long id) {
		return voteMapper.findById(id)
			.orElseThrow(() -> new NoSuchElementException("id 값으로 조회였으나 Vote 를 찾을 수 없습니다 <id = " + id + ">"));
	}

	public Vote findVoteByMemberId(Long memberId) {
		return voteMapper.findByMemberId(memberId)
			.orElseThrow(
				() -> new NoSuchElementException("memberId 값으로 조회였으나 Vote 를 찾을 수 없습니다 <memberId = " + memberId + ">"));
	}

	public List<Vote> findAllVotesByElectionId(Long electionId) {
		return voteMapper.findAllByElectionId(electionId);
	}

	public List<Vote> findAllVotesByCandidateId(Long candidateId) {
		return voteMapper.findAllByCandidateId(candidateId);
	}

	public boolean hasVotedBefore(Long memberId) {
		return voteMapper.findByMemberId(memberId).isPresent();
	}
}
