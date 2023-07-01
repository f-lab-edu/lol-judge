package edu.flab.member.dto;

import edu.flab.global.vo.RankTier;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberRankDto {
	private String profileUrl;
	private Integer judge_point;
	private String nickname;
	private RankTier rankTier;
}
