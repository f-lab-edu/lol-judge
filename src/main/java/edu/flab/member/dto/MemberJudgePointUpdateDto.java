package edu.flab.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberJudgePointUpdateDto {
	private Long id;

	@NotNull
	private Integer judgePoint;
}

