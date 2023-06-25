package edu.flab.member.dto;

import edu.flab.global.vo.RankTier;
import lombok.Builder;

@Builder
public record GameAccountUpdateDto(Long id, String loginId, String nickname, RankTier rankTier) {
}
