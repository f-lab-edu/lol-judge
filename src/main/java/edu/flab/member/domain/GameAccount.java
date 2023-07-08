package edu.flab.member.domain;

import org.hibernate.validator.constraints.Length;

import edu.flab.global.vo.RankTier;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Getter
public class GameAccount {
	private Long id;

	@NotNull
	private Long memberId;

	@NotBlank
	@Length(max = 16)
	private String nickname;

	@NotBlank
	@Length(max = 24)
	private String loginId;

	@NotNull
	private RankTier rankTier;

	public GameAccount(Long id, Long memberId, String nickname, String loginId) {
		this.id = id;
		this.memberId = memberId;
		this.nickname = nickname;
		this.loginId = loginId;
	}
}
