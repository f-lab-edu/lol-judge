package edu.flab.member.dto;

import edu.flab.global.validation.Password;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPasswordUpdateDto {
	private Long id;

	@Password
	private String password;
}
