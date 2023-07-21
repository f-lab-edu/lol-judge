package edu.flab.member.util;

import edu.flab.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberRankScoreUpdateEvent {
	private Member updatedMember;
}
