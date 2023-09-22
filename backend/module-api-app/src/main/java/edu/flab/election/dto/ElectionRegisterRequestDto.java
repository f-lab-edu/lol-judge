package edu.flab.election.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import edu.flab.election.config.ElectionRule;
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
	@Length(max = 500)
	private String opinion;

	@NotBlank
	@Length(max = 100)
	private String youtubeUrl;

	@NotBlank
	@Length(max = 300)
	private String participantEmail;

	@Range(min = ElectionRule.MIN_COST, max = ElectionRule.MAX_COST)
	private int cost;

	@Range(min = ElectionRule.MIN_PROGRESS_HOUR, max = ElectionRule.MAX_PROGRESS_HOUR)
	private int progressTime;

	@NotBlank
	@Length(max = 15)
	private String champion;
}
