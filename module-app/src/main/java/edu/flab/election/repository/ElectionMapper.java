package edu.flab.election.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import edu.flab.election.domain.Election;
import edu.flab.election.dto.ElectionContentsUpdateRequestDto;
import edu.flab.election.dto.ElectionEndTimeUpdateRequestDto;
import edu.flab.election.dto.ElectionFindOrderByTotalVotedCountDto;
import edu.flab.election.dto.ElectionFindRequestDto;
import edu.flab.election.dto.ElectionStatusUpdateRequestDto;
import edu.flab.election.dto.ElectionTotalVotedCountUpdateRequestDto;

@Mapper
public interface ElectionMapper {
	void save(Election election);

	void updateContentsById(ElectionContentsUpdateRequestDto dto);

	void updateStatusById(ElectionStatusUpdateRequestDto dto);

	void updateEndTimeById(ElectionEndTimeUpdateRequestDto dto);

	void updateTotalVotedCountById(ElectionTotalVotedCountUpdateRequestDto dto);

	void deleteById(Long id);

	Optional<Election> findPendingElectionById(Long id);

	Optional<Election> findInProgressElectionById(Long id);

	Optional<Election> findFinishedElectionById(Long id);

	List<Election> findPendingElections(ElectionFindRequestDto dto);

	List<Election> findInProgressElections(ElectionFindRequestDto dto);

	List<Election> findFinishedElections(ElectionFindRequestDto dto);

	List<Election> findInProgressElectionsOrderByTotalVotedCount(ElectionFindOrderByTotalVotedCountDto dto);

	List<Election> findFinishedElectionsOrderByTotalVotedCount(ElectionFindOrderByTotalVotedCountDto dto);
}
