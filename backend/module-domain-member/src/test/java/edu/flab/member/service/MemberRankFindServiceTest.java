package edu.flab.member.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.flab.member.dto.MemberRankRequestDto;
import edu.flab.member.repository.MemberMapper;

@ExtendWith(MockitoExtension.class)
class MemberRankFindServiceTest {

	@InjectMocks
	private MemberRankFindService sut;

	@Mock
	private MemberMapper memberMapper;

	@Test
	void 회원랭킹을_조회한다() {
		// given
		MemberRankRequestDto dto = MemberRankRequestDto.builder().upperBoundScore(1000).limit(10).build();

		// when
		sut.getMemberRankingOrderByRankScore(dto);

		// then
		Mockito.verify(memberMapper).findMemberRankingOrderByRankScore(dto);
	}
}
