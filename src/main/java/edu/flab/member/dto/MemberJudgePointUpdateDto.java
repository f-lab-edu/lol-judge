package edu.flab.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberJudgePointUpdateDto {
	private final Long id;
	private final int judgePoint;
}

