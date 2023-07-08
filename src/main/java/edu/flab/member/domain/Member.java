package edu.flab.member.domain;

import org.hibernate.validator.constraints.Length;
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
	@Email
	private String email;

	@Password
	private String password;

	@URL
	@Length(max = 200)
	private String profileUrl;

	@Default
	private JudgePoint judgePoint = JudgePoint.ZERO;

	private GameAccount gameAccount;

	private boolean deleted;

	public Member(Long id, String email, String password, String profileUrl, Boolean deleted) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.profileUrl = profileUrl;
		this.deleted = deleted;
	}
}
