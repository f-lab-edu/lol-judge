package edu.flab.member.dto;

import org.hibernate.validator.constraints.Range;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJudgePointUpdateDto {
	private Long id;

	@Range(min = 0, max = Integer.MAX_VALUE)
	private int judgePoint;
}

