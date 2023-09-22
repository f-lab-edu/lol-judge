package edu.flab.member.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

	/**
	 * 비밀번호가 다음 규칙을 만족하는지 검사한다.
	 * 숫자 포함, 영어 소문자 포함, 대문자 포함, 특수문자(!@#&()-{}:;',?/*~$^+=<>)으로 구성
	 * 최소 8글자에서 최대 20글자
	 */

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		if (password == null) {
			return false;
		}
		return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
	}
}
