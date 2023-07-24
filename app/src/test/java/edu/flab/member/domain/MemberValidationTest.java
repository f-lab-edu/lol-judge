package edu.flab.domain;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import edu.flab.member.domain.Member;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class MemberValidationTest {
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	void 비밀번호가_8글자이상이_아니라면_규칙위반이다() {
		// given
		Member member = Member.builder()
			.email("admin@example")
			.profileUrl("http://bucket.example")
			.password("aA!123")
			.build();

		// when
		Set<ConstraintViolation<Member>> violations = validator.validate(member);

		// then
		Assertions.assertThat(violations.stream().toList().size()).isEqualTo(1);
		Assertions.assertThat(violations.stream().toList().get(0).getInvalidValue()).isEqualTo(member.getPassword());
	}

	@ParameterizedTest
	@ValueSource(strings = {"aaa!1234", "AAA!1234", "aaAA1234"})
	void 비밀번호가_최소1개의_영문소문자_영문대문자_특수문자_숫자조합이_아니라면_규칙위반이다(String myPassword) {
		// given
		Member member = Member.builder()
			.email("admin@example")
			.profileUrl("http://bucket.example")
			.password(myPassword)
			.build();

		// when
		Set<ConstraintViolation<Member>> violations = validator.validate(member);

		// then
		Assertions.assertThat(violations.stream().toList().size()).isEqualTo(1);
		Assertions.assertThat(violations.stream().toList().get(0).getInvalidValue()).isEqualTo(myPassword);
	}

	@Test
	void 프로필링크가_URL_형식이_아니라면_유효성체크에_규칙위반이다() {
		// given
		Member member = Member.builder()
			.email("admin@example")
			.profileUrl("bucket.example")
			.password("aaAA1234!")
			.build();

		// when
		Set<ConstraintViolation<Member>> violations = validator.validate(member);

		// then
		System.out.println(violations);
		Assertions.assertThat(violations.stream().toList().size()).isEqualTo(1);
		Assertions.assertThat(violations.stream().toList().get(0).getInvalidValue()).isEqualTo(member.getProfileUrl());
	}

	@Test
	void 이메일이_이메일형식이_아니라면_규칙위반이다() {
		// given
		Member member = Member.builder()
			.email("admin.example")
			.profileUrl("http://bucket.example")
			.password("aaAA1234!")
			.build();

		// when
		Set<ConstraintViolation<Member>> violations = validator.validate(member);

		// then
		System.out.println(violations);
		Assertions.assertThat(violations.stream().toList().size()).isEqualTo(1);
		Assertions.assertThat(violations.stream().toList().get(0).getInvalidValue()).isEqualTo(member.getEmail());
	}
}

