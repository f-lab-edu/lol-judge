package edu.flab.member.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.flab.member.TestFixture;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.member.event.MemberSignUpEvent;
import edu.flab.member.repository.MemberJpaRepository;

@ExtendWith(MockitoExtension.class)
class MemberSignUpServiceTest {

	@InjectMocks
	private MemberSignUpService sut;

	@Mock
	private MemberJpaRepository memberJpaRepository;

	@Mock
	private ApplicationEventPublisher eventPublisher;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("회원가입 서비스는 전달받은 정보를 Member 객체로 변환하고, 비밀번호는 암호화하여 데이터베이스에 저장한다.")
	void test1() {
		// given
		Member member = TestFixture.getMember();

		MemberSignUpDto dto = MemberSignUpDto.builder()
			.email(member.getEmail())
			.password(member.getPassword())
			.profileUrl(member.getProfileUrl())
			.summonerName(member.getGameAccount().getEncryptedId())
			.build();

		when(memberJpaRepository.save(any(Member.class))).thenReturn(member);
		when(memberJpaRepository.existsByEmail(member.getEmail())).thenReturn(false);
		when(passwordEncoder.encode(anyString())).thenReturn("encrypted password");

		// when
		sut.signUp(dto);

		// then
		verify(memberJpaRepository).existsByEmail(member.getEmail());
		verify(memberJpaRepository).save(any(Member.class));
		verify(eventPublisher).publishEvent(any(MemberSignUpEvent.class));
	}
}
