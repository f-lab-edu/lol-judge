package edu.flab.member.domain;

import edu.flab.global.vo.LolTier;
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
	private String nickname;    // 리그오브레전드 닉네임
	private String lolLoginId;  // 리그오브레전드 로그인 아이디
	private LolTier lolTier;    // 리그오브레전드 랭크 티어

	public GameAccount(Long id, Long memberId, String nickname, String lolLoginId) {
		this.id = id;
		this.memberId = memberId;
		this.nickname = nickname;
		this.lolLoginId = lolLoginId;
	}
}
