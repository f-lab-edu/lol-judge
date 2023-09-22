package edu.flab.election.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.flab.election.domain.Candidate;

public interface CandidateJpaRepository extends JpaRepository<Candidate, Long> {

}
