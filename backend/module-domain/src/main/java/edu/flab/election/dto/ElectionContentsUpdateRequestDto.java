package edu.flab.election.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import edu.flab.election.config.ElectionRule;
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

	@URL
	@Length(max = 100)
	private String youtubeUrl;

	@Range(min = ElectionRule.MIN_COST, max = ElectionRule.MAX_COST)
	private int cost;
}
