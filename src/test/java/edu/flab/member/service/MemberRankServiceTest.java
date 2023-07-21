package edu.flab.member.service;

import static edu.flab.member.domain.LolTier.Color.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.flab.member.domain.GameAccount;
import edu.flab.member.domain.LolTier;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberRankRequestDto;
import edu.flab.member.repository.MemberMapper;

@ExtendWith(MockitoExtension.class)
class MemberRankServiceTest {

	@InjectMocks
	private MemberRankService sut;

	@Mock
	private MemberMapper memberMapper;

	private final LolTier challenger = LolTier.highTier(CHALLENGER, 1000);

	// given
	private final GameAccount gameAccount = GameAccount.builder()
		.lolLoginId("login12345")
		.memberId(1L)
		.nickname("hide on bush")
		.lolTier(challenger)
		.build();

	private final Member member = Member.builder()
		.id(1L)
		.email("user123@email.com")
		.password("1234")
		.profileUrl("abcd")
		.gameAccount(gameAccount)
		.build();

	@Test
	void 회원랭킹을_조회한다() {
		// given
		MemberRankRequestDto dto = MemberRankRequestDto.builder().upperBoundScore(1000).limit(10).build();

		// when
		sut.getMemberRankingOrderByRankScore(dto);

		// then
		verify(memberMapper).findMemberRankingOrderByRankScore(dto);
	}
}
