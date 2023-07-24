package edu.flab.service;

import static edu.flab.member.domain.LolTier.Color.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.flab.member.domain.GameAccount;
import edu.flab.member.domain.LolTier;
import edu.flab.member.domain.LolTierUtil;
import edu.flab.member.domain.Member;
import edu.flab.member.domain.RankScore;
import edu.flab.member.dto.MemberRankRequestDto;
import edu.flab.member.dto.MemberRankScoreUpdateDto;
import edu.flab.member.repository.MemberMapper;
import edu.flab.member.service.MemberRankService;

@ExtendWith(MockitoExtension.class)
class MemberRankServiceTest {

	@InjectMocks
	private MemberRankService sut;

	@Mock
	private MemberMapper memberMapper;

	private final LolTier challenger = LolTierUtil.createHighTier(CHALLENGER, 1000);

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
		Mockito.verify(memberMapper).findMemberRankingOrderByRankScore(dto);
	}

	@Test
	void 회원랭킹점수를_업데이트한다() {
		// given
		RankScore rankScore = RankScore.calc(member);
		MemberRankScoreUpdateDto dto = MemberRankScoreUpdateDto.builder()
			.id(member.getId())
			.rankScore(rankScore.getScore())
			.build();

		Mockito.when(memberMapper.findActiveMemberById(1L)).thenReturn(Optional.of(member));
		Mockito.doNothing().when(memberMapper).updateRankScore(dto);

		// when
		sut.updateRankScore(member.getId());

		// then
		Mockito.verify(memberMapper).findActiveMemberById(member.getId());
		Mockito.verify(memberMapper).updateRankScore(dto);
	}
}
