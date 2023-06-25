package edu.flab.member.domain;

import edu.flab.global.vo.RankTier;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
@Builder
public class GameAccount {
	private Long id;
	private String loginId;
	private String nickname;
	private RankTier rankTier;
}
