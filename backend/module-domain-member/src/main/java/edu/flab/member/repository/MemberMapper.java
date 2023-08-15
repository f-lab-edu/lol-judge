package edu.flab.member.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberJudgePointUpdateDto;
import edu.flab.member.dto.MemberPasswordUpdateDto;
import edu.flab.member.dto.MemberRankRequestDto;
import edu.flab.member.dto.MemberRankResponseDto;
import edu.flab.member.dto.MemberRankScoreUpdateDto;
import edu.flab.member.dto.MemberRequestDto;

@Mapper
public interface MemberMapper {
	void save(Member member);

	void updatePassword(MemberPasswordUpdateDto dto);

	void updateJudgePoint(MemberJudgePointUpdateDto dto);

	void updateRankScore(MemberRankScoreUpdateDto dto);

	void delete(Long id);

	Optional<Member> findActiveMemberById(Long id);

	Optional<Member> findInactiveMemberById(Long id);

	Optional<Member> findActiveMemberByEmail(String email);

	Optional<Member> findInactiveMemberByEmail(String email);

	List<Member> findActiveMembers(MemberRequestDto dto);

	List<Member> findInactiveMembers(MemberRequestDto dto);

	List<MemberRankResponseDto> findMemberRankingOrderByRankScore(MemberRankRequestDto dto);
}
