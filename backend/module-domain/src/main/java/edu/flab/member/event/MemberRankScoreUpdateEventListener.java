package edu.flab.member.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import edu.flab.member.domain.Member;
import edu.flab.member.domain.RankScore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberRankScoreUpdateEventListener {

	@Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener
	public void onApplicationEvent(MemberRankScoreUpdateEvent event) {
		Member updatedMember = event.getUpdatedMember();
		updateRankScore(updatedMember);
	}

	public void updateRankScore(Member member) {
		RankScore beforeRankScore = member.getRankScore();
		RankScore updatedRankScore = member.refreshRankScore();
		log.info("랭킹 점수 업데이트 완료 <회원 이메일: {}> <변경 내역: {} → {}>", member.getEmail(), beforeRankScore.getScore(),
			updatedRankScore.getScore());
	}
}
