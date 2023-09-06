package edu.flab.election.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Candidate {
	private Long id;

	@NotNull
	private Long memberId;

	@NotNull
	private Long electionId;

	private String opinion;

	private String champion;

	@NotNull
	private VotedStatus votedStatus;

	public Candidate(Long memberId, Long electionId, String opinion, String champion) {
		this.memberId = memberId;
		this.electionId = electionId;
		this.opinion = opinion;
		this.champion = champion;
	}

	public void setElectionId(Long electionId) {
		this.electionId = electionId;
	}
}
