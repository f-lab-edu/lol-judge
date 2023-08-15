package edu.flab.member.dto;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRankRequestDto {
	@Min(value = 0)
	private long upperBoundScore;

	@Min(value = 1)
	private int limit;
}
