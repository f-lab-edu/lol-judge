package edu.flab.member.dto;

import edu.flab.member.domain.LolTier;
import lombok.Builder;

@Builder
public record MemberRankResponseDto(String profileUrl, Integer judgePoint, String nickname, LolTier lolTier) {
}
