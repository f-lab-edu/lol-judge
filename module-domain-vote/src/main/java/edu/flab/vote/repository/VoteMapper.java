package edu.flab.vote.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import edu.flab.vote.domain.Vote;
import edu.flab.vote.dto.VoteUpdateRequestDto;

@Mapper
public interface VoteMapper {
	void save(Vote vote);

	void updateById(VoteUpdateRequestDto dto);

	void deleteById(Long id);

	Optional<Vote> findById(Long id);

	Optional<Vote> findByMemberId(Long memberId);

	List<Vote> findAllByElectionId(Long electionId);

	List<Vote> findAllByCandidateId(Long candidateId);
}
