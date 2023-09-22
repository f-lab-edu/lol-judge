package edu.flab.election.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.flab.election.domain.Vote;

public interface VoteJpaRepository extends JpaRepository<Vote, Long> {
	List<Vote> findAllByCandidateId(Long candidateId);

	Optional<Vote> findByMemberIdAndCandidateId(Long memberId, Long candidateId);
}
