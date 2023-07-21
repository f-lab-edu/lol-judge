package edu.flab.member.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import edu.flab.global.validation.Password;
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

	private GameAccount gameAccount;

	@Default
	private RankScore rankScore = RankScore.zero();

	private int judgePoint;

	private boolean deleted;

	public Member(Long id, String email, String password, String profileUrl, Integer judgePoint, Boolean deleted) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.profileUrl = profileUrl;
		this.judgePoint = judgePoint;
		this.deleted = deleted;
	}

	public RankScore updateRankScore() {
		return rankScore = RankScore.calc(this);
	}

	public void updateJudgePoint(int judgePoint) {
		this.judgePoint = judgePoint;
	}
}
