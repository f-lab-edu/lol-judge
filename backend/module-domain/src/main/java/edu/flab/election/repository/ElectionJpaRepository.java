package edu.flab.election.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.flab.election.domain.Election;
import edu.flab.election.domain.ElectionStatus;

public interface ElectionJpaRepository extends JpaRepository<Election, Long> {
	@Query("SELECT e FROM Election e WHERE e.status = :electionStatus AND e.endedAt <= :currentTime")
	List<Election> findByStatusAndEndedAt(ElectionStatus electionStatus, OffsetDateTime currentTime);

	List<Election> findByStatus(ElectionStatus electionStatus);

	Page<Election> findByStatus(ElectionStatus electionStatus, Pageable pageable);
}
