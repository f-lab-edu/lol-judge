package edu.flab.election.dto;

import edu.flab.election.domain.Election;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ElectionStatusUpdateRequestDto {
	private Long id;

	private Election.ElectionStatus status;
}
