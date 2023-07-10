package edu.flab.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberRequestDto {
	private final int offset;
	private final int limit;
}
