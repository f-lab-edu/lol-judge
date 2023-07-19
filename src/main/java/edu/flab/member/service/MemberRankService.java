package edu.flab.member.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import edu.flab.member.domain.Member;
import edu.flab.member.domain.RankScore;
import edu.flab.member.dto.MemberRankRequestDto;
import edu.flab.member.dto.MemberRankResponseDto;
import edu.flab.member.dto.MemberRankScoreUpdateDto;
import edu.flab.member.repository.MemberMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberRankService {
	private final MemberMapper memberMapper;

	/**
	 * Member.rankScore 의 변동요소인 Member.judgePoint, GameAccount.lolTier 변경을 감지하여 해당 메서드를 실행시킨다.
	 * Member 타입을 반환하여 클라이언트코드에서 사용할 수 있도록 편의성을 제공하고 싶다.
	 * 고민1. 데이터베이스에서 조회하여 반환하는 방법밖에 없는가? memberMapper.updateRankScore 쿼리문이 Member 객체를 반환하는 방법은 없는가?
	 */
	public Member updateRankScore(Long memberId) {
		Member member = memberMapper.findActiveMemberById(memberId)
			.orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다."));

		RankScore rankScore = member.updateRankScore();
		memberMapper.updateRankScore(new MemberRankScoreUpdateDto(memberId, rankScore.score()));
		return member;
	}

	public List<MemberRankResponseDto> getMemberRankingOrderByRankScore(MemberRankRequestDto dto) {
		return memberMapper.findMemberRankingOrderByRankScore(dto);
	}
}
