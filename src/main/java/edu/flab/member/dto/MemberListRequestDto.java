package edu.flab.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberListRequestDto {
	private final int offset;
	private final int limit;
}
