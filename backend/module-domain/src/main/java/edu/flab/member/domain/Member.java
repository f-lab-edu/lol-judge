package edu.flab.member.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import edu.flab.election.domain.Election;
import edu.flab.election.domain.Vote;
import edu.flab.member.domain.specification.JudgePointSpecification;
import edu.flab.notification.domain.Notification;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
@EqualsAndHashCode(exclude = {"gameAccount", "notifications", "votes"})
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

	@OneToOne(mappedBy = "member", cascade = CascadeType.PERSIST, optional = false, orphanRemoval = true)
	private GameAccount gameAccount;

	@Default
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private List<Election> elections = new ArrayList<>();

	@Default
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Notification> notifications = new ArrayList<>();

	@Default
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Vote> votes = new ArrayList<>();

	@Default
	@Embedded
	private RankScore rankScore = RankScore.zero();

	@Range(min = JudgePointSpecification.MIN_VALUE, max = JudgePointSpecification.MAX_VALUE)
	private int judgePoint;

	@Default
	private boolean deleted = false;

	@Default
	private boolean authenticated = false;

	public RankScore refreshRankScore() {
		return rankScore = RankScore.calc(this);
	}

	public void setJudgePoint(int judgePoint) {
		if (!JudgePointSpecification.isSatisfied(judgePoint)) {
			log.warn("judgePoint 범위를 벗어났습니다. <email = {}> <judgePoint = {}>", email, judgePoint);
			throw new IllegalArgumentException("judgePoint 범위를 벗어났습니다");
		}
		int beforeUpdate = this.judgePoint;
		this.judgePoint = judgePoint;
		log.info("JudgePoint 업데이트 완료 <회원 이메일: {}> <변경 내역: {} → {}>", email, beforeUpdate, this.judgePoint);
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	//== 연관관계 매핑 ==//
	public void setGameAccount(GameAccount gameAccount) {
		this.gameAccount = gameAccount;
		gameAccount.setMember(this);
	}

	public void addElection(Election election) {
		if (election.getMember() != null) {
			election.getMember().getElections().remove(election);
		}
		this.elections.add(election);
		election.setMember(this);
	}
}
