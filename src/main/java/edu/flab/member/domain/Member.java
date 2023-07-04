package edu.flab.member.domain;

import edu.flab.global.vo.JudgePoint;
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
	private String email;
	private String password;
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
