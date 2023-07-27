package edu.flab.election.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import edu.flab.election.domain.Candidate;

@Mapper
public interface CandidateMapper {
	void save(Candidate candidate);

	void updateVotedStatusById(Candidate candidate);

	void deleteById(Long id);

	Optional<Candidate> findById(Long id);
}
