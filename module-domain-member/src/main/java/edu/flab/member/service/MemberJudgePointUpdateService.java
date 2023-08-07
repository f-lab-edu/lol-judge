package edu.flab.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberJudgePointCalcDto;
import edu.flab.member.dto.MemberJudgePointUpdateDto;
import edu.flab.member.event.MemberRankScoreUpdateEventPublisher;
import edu.flab.member.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberJudgePointUpdateService {

	private final MemberMapper memberMapper;
	private final MemberFindService memberFindService;
	private final MemberRankScoreUpdateEventPublisher memberRankScoreUpdateEventPublisher;

	@Transactional
	public Member updateJudgePoint(Member member, int updatedJudgePoint) {
		int beforeUpdate = member.getJudgePoint();

		memberMapper.updateJudgePoint(new MemberJudgePointUpdateDto(member.getId(), updatedJudgePoint));
		member.updateJudgePoint(updatedJudgePoint);

		log.info("JudgePoint 업데이트 완료 <회원 이메일: {}> <변경 내역: {} → {}>", member.getEmail(), beforeUpdate,
			updatedJudgePoint);

		memberRankScoreUpdateEventPublisher.publishEvent(member);
		return member;
	}

	@Transactional
	public Member minusJudgePoint(MemberJudgePointCalcDto dto) {
		Member member = memberFindService.findActiveMember(dto.getId());
		return updateJudgePoint(member, member.getJudgePoint() - dto.getValue());
	}

	@Transactional
	public Member plusJudgePoint(MemberJudgePointCalcDto dto) {
		Member member = memberFindService.findActiveMember(dto.getId());
		return updateJudgePoint(member, member.getJudgePoint() + dto.getValue());
	}
}
