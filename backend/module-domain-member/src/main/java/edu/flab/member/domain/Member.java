package edu.flab.member.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import edu.flab.member.domain.specification.JudgePointSpecification;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode(exclude = "gameAccount")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {
	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Email
	private String email;

	@NotBlank
	private String password;

	@URL
	@Length(max = 200)
	private String profileUrl;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "game_account_id")
	private GameAccount gameAccount;

	@Default
	@Embedded
	private RankScore rankScore = RankScore.zero();

	@Range(min = JudgePointSpecification.MIN_VALUE, max = JudgePointSpecification.MAX_VALUE)
	private int judgePoint;

	@Default
	private boolean active = true;

	public RankScore refreshRankScore() {
		return rankScore = RankScore.calc(this);
	}

	public void setJudgePoint(int judgePoint) {
		this.judgePoint = judgePoint;
	}

	//== 연관관계 매핑 ==//
	public void setGameAccount(GameAccount gameAccount) {
		this.gameAccount = gameAccount;
		gameAccount.setMember(this);
	}
}
