package edu.flab.election.dto;

import org.hibernate.validator.constraints.Range;

import edu.flab.election.config.ElectionConstant;
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
public class ElectionContentsUpdateRequestDto {

	@NotNull
	private Long id;

	@NotBlank
	private String contents;

	@NotBlank
	private String youtubeUrl;

	@Range(min = ElectionConstant.MIN_COST, max = ElectionConstant.MAX_COST)
	private int cost;
}
