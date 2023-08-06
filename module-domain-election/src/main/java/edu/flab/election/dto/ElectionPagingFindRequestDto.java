package edu.flab.election.dto;

import edu.flab.election.domain.ElectionStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ElectionPagingFindRequestDto {
	private long offset;
	private long limit;
	private ElectionStatus electionStatus;
}
