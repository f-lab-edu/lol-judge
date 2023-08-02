package edu.flab.member.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import edu.flab.member.domain.LolTier;
import edu.flab.member.validation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignUpDto {
	@NotNull
	@Email
	@Length(max = 320)
	private String email;

	@Password
	private String password;

	@URL
	@Length(max = 200)
	private String profileUrl;

	@NotBlank
	@Length(max = 16)
	private String nickname;

	@NotBlank
	@Length(max = 24)
	private String gameLoginId;

	@NotNull
	private LolTier lolTier;
}
