package edu.flab.member.dto;

import edu.flab.global.validation.Password;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberPasswordUpdateDto {
	private Long id;

	@Password
	private String password;
}
