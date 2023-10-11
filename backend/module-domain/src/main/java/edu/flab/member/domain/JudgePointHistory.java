package edu.flab.member.domain;

import java.time.OffsetDateTime;

import edu.flab.election.domain.Election;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@EqualsAndHashCode(exclude = {"member", "election"})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JudgePointHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "election_id")
	private Election election;

	@Enumerated(EnumType.STRING)
	private JudgePointHistoryType type;

	private long amount;

	private OffsetDateTime createdAt;

	public JudgePointHistory(JudgePointHistoryType type, long amount) {
		this.type = type;
		this.amount = amount;
	}

	// 연관 관계 편의 메서드
	public void setMember(Member member) {
		if (this.member != null) {
			this.member.getJudgePointHistory().remove(this);
		}
		this.member = member;
		this.member.getJudgePointHistory().add(this);
	}

	public void setElection(Election election) {
		this.election = election;
	}
}
