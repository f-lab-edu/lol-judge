package edu.flab.election.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import edu.flab.election.config.ElectionConstant;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ElectionRegisterRequestDto {
	@NotBlank
	private String opinion;

	@NotBlank
	@Length(max = 100)
	private String youtubeUrl;

	@NotBlank
	@Length(max = 300)
	private String participantEmail;

	@Range(min = ElectionConstant.MIN_COST, max = ElectionConstant.MAX_COST)
	private int cost;

	@Range(min = ElectionConstant.MIN_PROGRESS_HOUR, max = ElectionConstant.MAX_PROGRESS_HOUR)
	private int progressTime;

	@NotBlank
	private String champion;
}
