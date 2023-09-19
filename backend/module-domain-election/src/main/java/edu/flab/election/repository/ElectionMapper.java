package edu.flab.election.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import edu.flab.election.domain.Election;
import edu.flab.election.domain.ElectionStatus;
import edu.flab.election.dto.ElectionContentsUpdateRequestDto;
import edu.flab.election.dto.ElectionEndTimeUpdateRequestDto;
import edu.flab.election.dto.ElectionFindRequestDto;
import edu.flab.election.dto.ElectionPagingFindRequestDto;
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

	Optional<Election> findElectionById(Long electionId);

	Optional<Election> findElectionByStatusAndId(ElectionFindRequestDto dto);

	List<Election> findAllElectionsByStatus(ElectionStatus status);

	List<Election> findAllElectionsByStatusWithPaging(ElectionPagingFindRequestDto dto);

	List<Election> findLatestElectionsByStatusWithPaging(ElectionPagingFindRequestDto dto);

	List<Election> findElectionsOrderByTotalVotedCount(ElectionPagingFindRequestDto dto);
}
