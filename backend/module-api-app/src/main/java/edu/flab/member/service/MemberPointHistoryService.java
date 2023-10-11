package edu.flab.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.flab.member.dto.MemberJudgePointHistoryDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberPointHistoryService {

	private final MemberFindService memberFindService;

	public List<MemberJudgePointHistoryDto> getPointHistory(Long memberId) {
		return memberFindService.findActiveMember(memberId)
			.getJudgePointHistory()
			.stream()
			.map(h -> new MemberJudgePointHistoryDto(h.getAmount(), h.getType()))
			.toList();
	}
}
