package edu.flab.member.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberRequestDto;
import edu.flab.member.repository.MemberMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberFindService {

	private final MemberMapper memberMapper;

	public Member findActiveMember(Long id) {
		return memberMapper.findActiveMemberById(id)
			.orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다" + "<id = " + id + ">"));
	}

	public Member findActiveMember(String email) {
		return memberMapper.findActiveMemberByEmail(email)
			.orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다" + "<email = " + email + ">"));
	}

	public List<Member> findActiveMembers(MemberRequestDto dto) {
		return memberMapper.findActiveMembers(dto);
	}

	public Member findInactiveMember(Long id) {
		return memberMapper.findInactiveMemberById(id)
			.orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다" + "<id = " + id + ">"));
	}

	public Member findInactiveMember(String email) {
		return memberMapper.findInactiveMemberByEmail(email)
			.orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다" + "<email = " + email + ">"));
	}

	public List<Member> findInactiveMembers(MemberRequestDto dto) {
		return memberMapper.findInactiveMembers(dto);
	}
}
