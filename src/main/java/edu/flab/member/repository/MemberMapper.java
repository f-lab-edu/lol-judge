package edu.flab.member.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberJudgePointUpdateDto;
import edu.flab.member.dto.MemberListRequestDto;
import edu.flab.member.dto.MemberPasswordUpdateDto;
import edu.flab.member.dto.MemberRankDto;

@Mapper
public interface MemberMapper {
	void save(Member member);

	void updatePassword(MemberPasswordUpdateDto dto);

	void updateJudgePoint(MemberJudgePointUpdateDto dto);

	void delete(Long id);

	Optional<Member> findActiveMemberById(Long id);

	Optional<Member> findDeletedMemberById(Long id);

	Optional<Member> findActiveMemberByEmail(String email);

	Optional<Member> findDeletedMemberByEmail(String email);

	List<Member> findActiveMembers(MemberListRequestDto dto);

	List<Member> findDeletedMembers(MemberListRequestDto dto);

	// NO OFFSET 방식으로 해당 쿼리를 작성하려면 무엇을 매개변수로 넘겨주어야할까?
	List<MemberRankDto> findMemberRankingOrderByJudgePoint();
}
