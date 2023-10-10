package edu.flab.member.dto;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class MemberSignUpDtoTest {

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	@DisplayName("비밀번호가 8글자이상이 아니라면 규칙위반이다")
	void test1() {
		// given
		MemberSignUpDto signUpDto = MemberSignUpDto.builder()
			.email("admin@example")
			.profileUrl("http://bucket.example")
			.summonerName("킹메준")
			.position("MID")
			.password("aA!123")
			.build();

		// when
		Set<ConstraintViolation<MemberSignUpDto>> violations = validator.validate(signUpDto);

		// then
		Assertions.assertThat(violations.stream().toList().size()).isEqualTo(1);
		Assertions.assertThat(violations.stream().toList().get(0).getInvalidValue()).isEqualTo(signUpDto.getPassword());
	}

	@ParameterizedTest
	@ValueSource(strings = {"aaa!1234", "AAA!1234", "aaAA1234"})
	@DisplayName("비밀번호가 최소1개의 영문소문자 영문대문자, 특수문자, 숫자조합이 아니라면 규칙위반이다")
	void test2(String myPassword) {
		// given
		MemberSignUpDto signUpDto = MemberSignUpDto.builder()
			.email("admin@example")
			.profileUrl("http://bucket.example")
			.summonerName("킹메준")
			.position("MID")
			.password(myPassword)
			.build();

		// when
		Set<ConstraintViolation<MemberSignUpDto>> violations = validator.validate(signUpDto);

		// then
		Assertions.assertThat(violations.stream().toList().size()).isEqualTo(1);
		Assertions.assertThat(violations.stream().toList().get(0).getInvalidValue()).isEqualTo(myPassword);
	}

	@Test
	@DisplayName("프로필링크가 URL 형식이 아니라면 유효성체크에 규칙위반이다")
	void test3() {
		// given
		MemberSignUpDto signUpDto = MemberSignUpDto.builder()
			.email("admin@example")
			.profileUrl("bucket.example")
			.summonerName("킹메준")
			.position("MID")
			.password("aA!12345")
			.build();

		// when
		Set<ConstraintViolation<MemberSignUpDto>> violations = validator.validate(signUpDto);

		// then
		System.out.println(violations);
		Assertions.assertThat(violations.stream().toList().size()).isEqualTo(1);
		Assertions.assertThat(violations.stream().toList().get(0).getInvalidValue())
			.isEqualTo(signUpDto.getProfileUrl());
	}
}
