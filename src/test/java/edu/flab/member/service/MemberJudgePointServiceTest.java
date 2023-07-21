package edu.flab.member.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberJudgePointUpdateDto;
import edu.flab.member.repository.MemberMapper;

@ExtendWith(MockitoExtension.class)
class MemberJudgePointServiceTest {

	@InjectMocks
	private MemberJudgePointService sut;

	@Mock
	private MemberMapper memberMapper;

	@Test
	@DisplayName("회원의 JudgePoint 를 변경할 수 있다")
	void test1() {
		// given
		Member member = Member.builder().email("example@example.com").password("1234").build();
		MemberJudgePointUpdateDto dto = MemberJudgePointUpdateDto.builder().id(1L).judgePoint(1000).build();
		Mockito.when(memberMapper.findActiveMemberById(1L)).thenReturn(Optional.of(member));

		// when
		Member updatedMember = sut.updateJudgePoint(dto);

		// then
		Mockito.verify(memberMapper).findActiveMemberById(1L);
		Mockito.verify(memberMapper).updateJudgePoint(dto);
		assertThat(updatedMember.getJudgePoint()).isEqualTo(dto.getJudgePoint());
	}
}
