package edu.flab.election.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import edu.flab.election.domain.Vote;
import edu.flab.election.repository.VoteJpaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteFindService {
	private final VoteJpaRepository voteJpaRepository;

	public Vote findVoteById(Long id) {
		return voteJpaRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("id 값으로 조회였으나 Vote 를 찾을 수 없습니다 <id = " + id + ">"));
	}

	public List<Vote> findAllVotesByCandidateId(Long candidateId) {
		return voteJpaRepository.findAllByCandidateId(candidateId);
	}

	public boolean hasVotedBefore(Long memberId, Long candidateId) {
		return voteJpaRepository.existsByMemberIdAndCandidateId(memberId, candidateId);
	}
}
