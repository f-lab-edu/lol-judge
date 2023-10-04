package edu.flab.election.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.flab.election.domain.Election;
import edu.flab.election.domain.ElectionStatus;

public interface ElectionJpaRepository extends JpaRepository<Election, Long> {
	List<Election> findByStatus(ElectionStatus electionStatus);

	Page<Election> findByStatus(ElectionStatus electionStatus, Pageable pageable);
}
