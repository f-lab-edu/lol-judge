package edu.flab.election.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import edu.flab.election.domain.Candidate;
import edu.flab.election.dto.CandidateUpdateOpinionRequestDto;

@Mapper
public interface CandidateMapper {
	void save(Candidate candidate);

	void updateOpinionById(CandidateUpdateOpinionRequestDto dto);

	void updateVotedStatusById(Candidate candidate);

	void deleteById(Long id);

	Optional<Candidate> findById(Long id);
}
