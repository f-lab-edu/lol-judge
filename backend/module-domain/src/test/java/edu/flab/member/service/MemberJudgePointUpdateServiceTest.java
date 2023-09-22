package edu.flab.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberJudgePointCalcDto;
import edu.flab.member.dto.MemberJudgePointUpdateDto;
import edu.flab.member.event.MemberRankScoreUpdateEventPublisher;

@ExtendWith(MockitoExtension.class)
class MemberJudgePointUpdateServiceTest {

	@InjectMocks
	private MemberJudgePointUpdateService sut;

	@Mock
	private MemberFindService memberFindService;

	@Mock
	private MemberRankScoreUpdateEventPublisher eventPublisher;

	@Test
	@DisplayName("회원의 JudgePoint 를 변경할 수 있다")
	void test1() {
		// given
		Member member = Member.builder().id(1L).email("example@example.com").password("1234").judgePoint(500).build();
		MemberJudgePointCalcDto dto = new MemberJudgePointCalcDto(1L, 1000);
		MemberJudgePointUpdateDto updateDto = new MemberJudgePointUpdateDto(1L, 1500);

		when(memberFindService.findActiveMember(1L)).thenReturn(member);
		doNothing().when(eventPublisher).publishEvent(member);

		// when
		Member updatedMember = sut.plusJudgePoint(dto);

		// then
		verify(memberFindService).findActiveMember(1L);
		verify(eventPublisher).publishEvent(member);
		assertThat(updatedMember.getJudgePoint()).isEqualTo(updateDto.getJudgePoint());
	}
}
