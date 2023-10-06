package edu.flab.member.domain.specification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JudgePointSpecification {
	public static final int MIN_VALUE = 0;
	public static final int MAX_VALUE = 10000000;

	public static boolean isSatisfied(int judgePoint) {
		return MIN_VALUE <= judgePoint && judgePoint <= MAX_VALUE;
	}
}
