package edu.flab.election.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import edu.flab.election.domain.Vote;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;

public interface VoteJpaRepository extends JpaRepository<Vote, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
	List<Vote> findAllByCandidateId(Long candidateId);

	Optional<Vote> findByMemberIdAndCandidateId(Long memberId, Long candidateId);
}
