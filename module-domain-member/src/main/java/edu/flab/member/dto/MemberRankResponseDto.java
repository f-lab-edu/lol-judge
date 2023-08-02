package edu.flab.member.dto;

import edu.flab.member.domain.LolTier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRankResponseDto {
	private String profileUrl;
	private int judgePoint;
	private long rankScore;
	private String nickname;
	private LolTier lolTier;

	/**
	 * MyBatis Mapping(DB record → MemberRankResponseDto)에 사용되는 생성자
	 */
	public MemberRankResponseDto(String profileUrl, Integer judgePoint, Long rankScore, String nickname) {
		this.profileUrl = profileUrl;
		this.judgePoint = judgePoint;
		this.rankScore = rankScore;
		this.nickname = nickname;
	}
}
