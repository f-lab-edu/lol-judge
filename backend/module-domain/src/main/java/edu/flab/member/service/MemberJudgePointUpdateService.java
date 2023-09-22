package edu.flab.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.member.domain.Member;
import edu.flab.member.domain.specification.JudgePointSpecification;
import edu.flab.member.dto.MemberJudgePointCalcDto;
import edu.flab.member.event.MemberRankScoreUpdateEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberJudgePointUpdateService {

	private final MemberFindService memberFindService;
	private final MemberRankScoreUpdateEventPublisher memberRankScoreUpdateEventPublisher;

	@Transactional
	public Member updateJudgePoint(Member member, int updatedJudgePoint) {
		validateJudgePoint(member, updatedJudgePoint);

		int beforeUpdate = member.getJudgePoint();

		member.setJudgePoint(updatedJudgePoint);

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

	private void validateJudgePoint(Member member, int judgePoint) {
		JudgePointSpecification spec = new JudgePointSpecification(judgePoint);
		if (!spec.isSatisfied()) {
			log.warn("judgePoint 범위를 벗어났습니다. <email = {}> <judgePoint = {}>", member.getEmail(), judgePoint);
			throw new IllegalArgumentException("judgePoint 범위를 벗어났습니다");
		}
	}
}
