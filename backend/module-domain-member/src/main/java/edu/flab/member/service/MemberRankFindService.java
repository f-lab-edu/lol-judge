package edu.flab.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.flab.member.dto.MemberRankRequestDto;
import edu.flab.member.dto.MemberRankResponseDto;
import edu.flab.member.repository.MemberMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberRankFindService {
	private final MemberMapper memberMapper;
	
	public List<MemberRankResponseDto> getMemberRankingOrderByRankScore(MemberRankRequestDto dto) {
		return memberMapper.findMemberRankingOrderByRankScore(dto);
	}
}
