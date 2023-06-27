package edu.flab.member.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberJudgePointUpdateDto;
import edu.flab.member.dto.MemberPasswordUpdateDto;

@Mapper
public interface MemberMapper {
	void save(Member member);

	void updatePassword(MemberPasswordUpdateDto dto);

	void updateJudgePoint(MemberJudgePointUpdateDto dto);

	void delete(Long id);

	Optional<Member> findById(Long id);

	Optional<Member> findByEmail(String email);

	List<Member> findAll();

	List<Member> findAllOrderByJudgePoint(long offset, long limit);
}
