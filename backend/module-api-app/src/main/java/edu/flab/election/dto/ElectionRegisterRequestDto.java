package edu.flab.election.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import edu.flab.election.config.ElectionRule;
import edu.flab.election.domain.Opinion;
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
public class ElectionRegisterRequestDto {
	@NotBlank
	@Length(max = 300)
	private String title;

	@NotBlank
	@Length(max = 100)
	private String youtubeUrl;

	@Range(min = ElectionRule.MIN_PROGRESS_HOUR, max = ElectionRule.MAX_PROGRESS_HOUR)
	private int progressTime;

	@NotNull
	private List<Opinion> opinions;
}
