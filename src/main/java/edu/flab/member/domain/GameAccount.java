package edu.flab.member.domain;

import edu.flab.global.vo.RankTier;
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
	private Long memberId;
	private String nickname;
	private String loginId;
	private RankTier rankTier;

	public GameAccount(Long id, Long memberId, String nickname, String loginId) {
		this.id = id;
		this.memberId = memberId;
		this.nickname = nickname;
		this.loginId = loginId;
	}
}
