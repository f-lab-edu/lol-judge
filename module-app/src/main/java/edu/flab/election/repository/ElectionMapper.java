package edu.flab.election.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import edu.flab.election.domain.Election;
import edu.flab.election.dto.ElectionFindOrderByTotalVotedCntDto;
import edu.flab.election.dto.ElectionFindRequestDto;

@Mapper
public interface ElectionMapper {
	void save(Election election);

	void updateById(Election election);

	void deleteById(Long id);

	Optional<Election> findPendingElectionById(Long id);

	Optional<Election> findInProgressElectionById(Long id);

	Optional<Election> findFinishedElectionById(Long id);

	List<Election> findPendingElections(ElectionFindRequestDto dto);

	List<Election> findInProgressElections(ElectionFindRequestDto dto);

	List<Election> findFinishedElections(ElectionFindRequestDto dto);

	List<Election> findInProgressElectionsOrderByTotalVotedCount(ElectionFindOrderByTotalVotedCntDto dto);

	List<Election> findFinishedElectionsOrderByTotalVotedCount(ElectionFindOrderByTotalVotedCntDto dto);
}
