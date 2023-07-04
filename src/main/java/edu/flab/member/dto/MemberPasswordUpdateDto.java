package edu.flab.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberPasswordUpdateDto {
	private final Long id;
	private final String password;
}
