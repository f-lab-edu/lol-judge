package edu.flab.member.dto;

import edu.flab.global.vo.LolTier;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberRankDto {
	private final String profileUrl;
	private final Integer judge_point;
	private final String nickname;
	private final LolTier lolTierGroup;
}
