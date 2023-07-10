package edu.flab.member.dto;

import edu.flab.global.vo.RankTier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRankResponseDto {
	private String profileUrl;
	private Integer judge_point;
	private String nickname;
	private RankTier rankTier;
}
