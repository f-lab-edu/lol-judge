package edu.flab.election.dto;

import edu.flab.election.domain.ElectionStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ElectionInfoFindRequestDto {
	@PositiveOrZero
	private int pageNumber;

	@PositiveOrZero
	private int pageSize;

	@NotNull
	private ElectionStatus status;
}
