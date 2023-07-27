package edu.flab.global.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
	String message() default "비밀번호는 숫자, 영문 소문자, 영문 대문자, 특수문자를 포함하여 최소 8글자 이상이어야 합니다";

	Class<?>[] groups() default {};

	Class<?>[] payload() default {};
}
