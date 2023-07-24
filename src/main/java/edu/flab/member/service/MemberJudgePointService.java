package edu.flab.member.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberJudgePointUpdateDto;
import edu.flab.member.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberJudgePointService {

	private final MemberMapper memberMapper;

	@Transactional
	public Member updateJudgePoint(MemberJudgePointUpdateDto dto) {
		Member member = memberMapper.findActiveMemberById(dto.getId())
			.orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다."));

		int beforeUpdate = member.getJudgePoint();

		memberMapper.updateJudgePoint(dto);
		member.updateJudgePoint(dto.getJudgePoint());

		log.info("JudgePoint 업데이트 완료 <회원 이메일: {}> <변경 내역: {} → {}>", member.getEmail(), beforeUpdate,
			member.getJudgePoint());

		return member;
	}
}
