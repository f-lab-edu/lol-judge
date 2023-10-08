package edu.flab.member.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import edu.flab.member.domain.Member;
import edu.flab.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberFindService {

	private final MemberJpaRepository memberJpaRepository;

	public Member findActiveMember(Long id) {
		return memberJpaRepository.findByIdAndDeletedAndAuthenticated(id, false, true)
			.orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다" + "<id = " + id + ">"));
	}

	public Member findActiveMember(String email) {
		return memberJpaRepository.findByEmailAndDeletedAndAuthenticated(email, false, true)
			.orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다" + "<email = " + email + ">"));
	}

	public Member findInactiveMember(Long id) {
		return memberJpaRepository.findByIdAndDeletedAndAuthenticated(id, true, true)
			.orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다" + "<id = " + id + ">"));
	}

	public Member findInactiveMember(String email) {
		return memberJpaRepository.findByEmailAndDeletedAndAuthenticated(email, true, true)
			.orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다" + "<email = " + email + ">"));
	}
}
