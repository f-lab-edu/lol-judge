package edu.flab.member.dto;

import org.hibernate.validator.constraints.Length;

import edu.flab.member.domain.LolTier;
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
public class GameAccountUpdateDto {

	private Long id;

	@NotBlank
	@Length(max = 24)
	private String lolLoginId;

	@NotBlank
	@Length(max = 16)
	private String nickname;

	@NotNull
	private LolTier lolTier;
}
