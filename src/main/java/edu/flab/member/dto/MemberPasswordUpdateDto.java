package edu.flab.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberPasswordUpdateDto {
	private Long id;
	private String password;
}
