package edu.flab.global.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode
@Builder
@Getter
public class JudgePoint {
	public static JudgePoint ZERO = new JudgePoint(0);

	private final Integer point;
}
