package edu.flab.member.domain;

import org.hibernate.validator.constraints.URL;

import edu.flab.global.validation.Password;
import edu.flab.global.vo.JudgePoint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Getter
public class Member {
	private Long id;

	@NotNull
	@Email(message = "이메일 형식만 입력 가능합니다")
	private String email;

	@NotNull
	@Password(message = "패스워드 규칙은 영소문자, 영대문자, 특수문자를 포함한 최소 8글자입니다")
	private String password;

	@NotNull
	@URL(message = "URL 형식만 입력 가능합니다")
	private String profileUrl;
	private boolean deleted;

	@Default
	private JudgePoint judgePoint = JudgePoint.ZERO;
	private GameAccount gameAccount;

	public Member(Long id, String email, String password, String profileUrl, Boolean deleted) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.profileUrl = profileUrl;
		this.deleted = deleted;
	}
}
