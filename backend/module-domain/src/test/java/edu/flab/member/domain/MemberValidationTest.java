package edu.flab.member.domain;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class MemberValidationTest {
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	@DisplayName("이메일이 이메일형식이 아니라면 규칙위반이다")
	void test4() {
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

