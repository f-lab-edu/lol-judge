package edu.flab.election.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

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
public class ElectionEditRequestDto {
	@NotNull
	private Long id;

	@URL
	@Length(max = 100)
	private String youtubeUrl;

	@Range(min = ElectionConstant.MIN_COST, max = ElectionConstant.MAX_COST)
	private int cost;

	@NotBlank
	@Length(max = 15)
	private String hostChampion;

	@NotBlank
	@Length(max = 500)
	private String hostOpinion;

	@NotBlank
	@Length(max = 15)
	private String participantChampion;

	@NotBlank
	@Length(max = 500)
	private String participantOpinion;
}
