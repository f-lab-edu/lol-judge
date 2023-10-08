package edu.flab.election.domain;

import edu.flab.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode(exclude = {"member", "candidate"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;

	public boolean isGroupOfWinner() {
		return candidate.getVotedStatus() == VotedStatus.WIN;
	}

	//== 연관관계 편의 메서드 ==//
	public void setMember(Member member) {
		if (this.member != null) {
			this.member.getVotes().remove(this);
		}
		this.member = member;
		member.getVotes().add(this);
	}

	public void setCandidate(Candidate candidate) {
		if (this.candidate != null) {
			this.candidate.getVotes().remove(this);
			this.candidate.minusScore(this.member.getGameAccount().getLolTier().getColor().getScore());
		}
		this.candidate = candidate;
		this.candidate.plusScore(this.member.getGameAccount().getLolTier().getColor().getScore());
		candidate.getVotes().add(this);
	}
}
