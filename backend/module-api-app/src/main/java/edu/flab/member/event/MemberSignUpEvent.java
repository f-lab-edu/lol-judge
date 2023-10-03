package edu.flab.member.event;

import edu.flab.member.domain.Member;

public record MemberSignUpEvent(Member member, String summonerName, String gamePosition) {
}
