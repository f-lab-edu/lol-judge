package edu.flab.election.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CandidateUpdateOpinionRequestDto {
	@NotNull
	private Long id;

	@NotBlank
	@Length(max = 15)
	private String champion;

	@NotBlank
	@Length(max = 500)
	private String opinion;
}
