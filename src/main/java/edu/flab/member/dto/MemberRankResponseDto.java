package edu.flab.member.dto;

import edu.flab.global.vo.LolTier;
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
	private Integer judgePoint;
	private String nickname;
	private LolTier lolTier;
}
