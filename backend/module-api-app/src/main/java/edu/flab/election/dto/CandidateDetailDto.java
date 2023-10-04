package edu.flab.election.dto;

import edu.flab.election.domain.Opinion;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CandidateDetailDto {
	@NotNull
	private Long candidateId;

	@NotNull
	private Opinion opinion;
}
