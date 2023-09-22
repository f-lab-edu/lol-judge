package edu.flab.election.domain;

import edu.flab.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString(exclude = "election")
@EqualsAndHashCode(exclude = {"member", "election"})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Candidate {
	@Id
	@GeneratedValue
	private Long id;

	@JoinColumn(name = "member_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@JoinColumn(name = "election_id")
	@ManyToOne
	private Election election;

	@Default
	@NotNull
	@Enumerated(EnumType.STRING)
	private VotedStatus votedStatus = VotedStatus.UNKNOWN;

	@NotNull
	@Enumerated(EnumType.STRING)
	private CandidateStatus candidateStatus;

	private String opinion;

	private String champion;

	public Candidate changeContents(String opinion, String champion) {
		this.opinion = opinion;
		this.champion = champion;
		return this;
	}

	//== 연관관계 편의 메서드 ==//
	public void setElection(Election election) {
		if (this.election != null) {
			this.election.getCandidates().remove(this);
		}
		this.election = election;
		election.getCandidates().add(this);
	}

	public void setMember(Member member) {
		if (this.member != null) {
			this.member.getCandidates().remove(this);
		}
		this.member = member;
		member.getCandidates().add(this);
	}
}
