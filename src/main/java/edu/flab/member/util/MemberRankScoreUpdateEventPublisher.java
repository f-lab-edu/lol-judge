package edu.flab.member.util;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import edu.flab.member.domain.Member;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberRankScoreUpdateEventPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;

	public void publishEvent(final Member member) {
		MemberRankScoreUpdateEvent event = new MemberRankScoreUpdateEvent(member);
		applicationEventPublisher.publishEvent(event);
	}
}
