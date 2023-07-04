package edu.flab.member.dto;

import edu.flab.global.vo.RankTier;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameAccountUpdateDto {
	private final Long id;
	private final Long memberId;
	private final String lolLoginId;
	private final String nickname;
	private final RankTier rankTier;
}
