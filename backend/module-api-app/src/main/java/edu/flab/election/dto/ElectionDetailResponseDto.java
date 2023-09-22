package edu.flab.election.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import edu.flab.election.config.ElectionRule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ElectionDetailResponseDto {
	private Long id;

	@Length(max = 50)
	private String youtubeUrl;

	@Range(min = ElectionRule.MIN_COST, max = ElectionRule.MAX_COST)
	private int cost;

	private String hostChampion;

	private String hostOpinion;

	private String participantChampion;

	private String participantOpinion;

	private Long hostId;

	private Long participantId;
}
