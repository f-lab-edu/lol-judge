package edu.flab.election.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@EqualsAndHashCode(exclude = {"election", "votes"})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Candidate implements Comparable<Candidate> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "election_id")
	@ManyToOne
	private Election election;

	@Default
	@OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
	private List<Vote> votes = new ArrayList<>();

	@NotNull
	@Default
	@Enumerated(EnumType.STRING)
	private VotedStatus votedStatus = VotedStatus.UNKNOWN;

	@Embedded
	private Opinion opinion;

	private long votedScore;

	public Candidate(Opinion opinion) {
		this.opinion = opinion;
	}

	public void setVotedStatus(VotedStatus votedStatus) {
		this.votedStatus = votedStatus;
	}

	public void plusScore(long score) {
		this.votedScore += score;
	}

	public void minusScore(long score) {
		this.votedScore -= score;
	}

	@Override
	public int compareTo(Candidate other) {
		return Long.compare(this.votedScore, other.votedScore);
	}

	//== 연관관계 편의 메서드 ==//
	public void setElection(Election election) {
		this.election = election;
	}
}
