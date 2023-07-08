package edu.flab.global.vo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Builder
@Getter
public class JudgePoint {
	public static JudgePoint ZERO = new JudgePoint(0);

	private final int point;
}
