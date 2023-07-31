package edu.flab.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.flab.TestFixture;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberRequestDto;
import edu.flab.member.repository.MemberMapper;

@ExtendWith(MockitoExtension.class)
class MemberFindServiceTest {

	@InjectMocks
	private MemberFindService sut;

	@Mock
	private MemberMapper memberMapper;

	@Test
	@DisplayName("ID 를 통해 회원을 찾는다")
	void test1() {
		// given
		Member member = TestFixture.getMember();
		when(memberMapper.findActiveMemberById(1L)).thenReturn(Optional.of(member));

		// when
		Member findMember = sut.findActiveMember(1L);

		// then
		assertThat(findMember).isEqualTo(member);
	}

	@Test
	@DisplayName("Email 을 통해 회원을 찾는다")
	void test2() {
		// given
		String findEmail = "user123@email.com";
		Member member = TestFixture.getMember();
		when(memberMapper.findActiveMemberByEmail(findEmail)).thenReturn(Optional.of(member));

		// when
		Member findMember = sut.findActiveMember(findEmail);

		// then
		assertThat(findMember).isEqualTo(member);
	}

	@Test
	@DisplayName("모든 회원을 찾는다")
	void test3() {
		// given
		Member member = TestFixture.getMember();
		MemberRequestDto dto = MemberRequestDto.builder().offset(0).limit(10).build();
		when(memberMapper.findActiveMembers(dto)).thenReturn(List.of(member));

		// when
		List<Member> findMembers = sut.findActiveMembers(dto);

		// then
		assertThat(findMembers).isEqualTo(List.of(member));
	}

	@Test
	@DisplayName("ID 를 통해 탈퇴 회원을 찾는다")
	void test4() {
		// given
		Member member = TestFixture.getMember();
		when(memberMapper.findInactiveMemberById(1L)).thenReturn(Optional.of(member));

		// when
		Member findMember = sut.findInactiveMember(1L);

		// then
		assertThat(findMember).isEqualTo(member);
	}

	@Test
	@DisplayName("Email 을 통해 탈퇴 회원을 찾는다")
	void test5() {
		// given
		String findEmail = "user123@email.com";
		Member member = TestFixture.getMember();
		when(memberMapper.findInactiveMemberByEmail(findEmail)).thenReturn(Optional.of(member));

		// when
		Member findMember = sut.findInactiveMember(findEmail);

		// then
		assertThat(findMember).isEqualTo(member);
	}

	@Test
	@DisplayName("모든 탈퇴 회원을 찾는다")
	void test6() {
		// given
		Member member = TestFixture.getMember();
		MemberRequestDto dto = MemberRequestDto.builder().offset(0).limit(10).build();
		when(memberMapper.findInactiveMembers(dto)).thenReturn(List.of(member));

		// when
		List<Member> findMembers = sut.findInactiveMembers(dto);

		// then
		assertThat(findMembers).isEqualTo(List.of(member));
	}
}
