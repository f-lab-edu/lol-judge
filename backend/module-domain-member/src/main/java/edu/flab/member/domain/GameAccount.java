package edu.flab.member.domain;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameAccount {
	private Long id;

	@NotNull
	private Long memberId;

	@NotBlank
	@Length(max = 16)
	private String nickname;    // 리그오브레전드 닉네임

	@NotBlank
	@Length(max = 24)
	private String lolLoginId;  // 리그오브레전드 계정 아이디

	@NotNull
	private GamePosition position;	// 리그오브레전드 포지션

	private LolTier lolTier;    // 리그오브레전드 랭크 티어 정보

	public GameAccount(Long id, Long memberId, String nickname, String lolLoginId) {
		this.id = id;
		this.memberId = memberId;
		this.nickname = nickname;
		this.lolLoginId = lolLoginId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
}
