package edu.flab.member.event;

import static edu.flab.member.domain.LolTier.Color.*;
import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.event.RecordApplicationEvents;

import edu.flab.member.domain.LolTierUtil;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberJudgePointUpdateDto;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.member.repository.MemberMapper;
import edu.flab.member.service.MemberJudgePointService;
import edu.flab.member.service.MemberSignUpService;

@Tag("integration")
@SpringBootTest
@RecordApplicationEvents
class MemberRankScoreUpdateEventListenerTest {
	@Autowired
	private MemberSignUpService memberSignUpService;

	@Autowired
	private MemberJudgePointService memberJudgePointService;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Test
	@DisplayName("JudgePoint 가 업데이트되면 RankScore 값이 계산된다")
	void test1() throws InterruptedException {
		// given
		MemberSignUpDto signUpDto = MemberSignUpDto.builder()
			.email("admin@example")
			.profileUrl("http://bucket.example")
			.password("aA!123")
			.nickname("user123-nickname")
			.gameLoginId("user123-lolLoginId")
			.lolTier(LolTierUtil.createHighTier(CHALLENGER, 300))
			.build();

		Member member = memberSignUpService.signUp(signUpDto);

		// when
		memberJudgePointService.updateJudgePoint(new MemberJudgePointUpdateDto(member.getId(), 1000));

		// 100ms 동안 Blocking, Thread Queue 에 있는 Task 가 완료되기를 기다린다.
		taskExecutor.getThreadPoolExecutor().awaitTermination(100, TimeUnit.MILLISECONDS);

		// then
		Member findMember = memberMapper.findActiveMemberById(member.getId()).orElseThrow();
		System.out.println(findMember.getRankScore().toString());
		assertThat(findMember.getRankScore()).isEqualTo(findMember.updateRankScore());
	}
}
