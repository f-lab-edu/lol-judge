package edu.flab.election.domain;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import edu.flab.election.config.ElectionRule;
import edu.flab.member.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
@ToString
@EqualsAndHashCode(exclude = "candidates")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Election {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Default
	@OneToMany(mappedBy = "election", cascade = CascadeType.PERSIST)
	private List<Candidate> candidates = new ArrayList<>();

	@Default
	@Enumerated(EnumType.STRING)
	private ElectionStatus status = ElectionStatus.IN_PROGRESS;

	@NotBlank
	@Length(max = 150)
	private String title;

	@URL
	@Length(max = 50)
	private String youtubeUrl;

	@URL
	@Length(max = 300)
	private String thumbnailUrl;

	@PositiveOrZero
	private long totalVotedCount;

	@Range(min = ElectionRule.MIN_PROGRESS_HOUR, max = ElectionRule.MAX_PROGRESS_HOUR)
	private int progressTime;

	@NotNull
	private OffsetDateTime createdAt;

	@NotNull
	private OffsetDateTime endedAt;

	private boolean deleted;

	public void setStatus(ElectionStatus status) {
		this.status = status;
	}

	//== 연관 관계 편의 메서드 ==//
	public void addCandidate(Candidate candidate) {
		if (candidate.getElection() != null) {
			candidate.getElection().getCandidates().remove(candidate);
		}
		candidate.setElection(this);
		this.candidates.add(candidate);
	}

	public void setMember(Member member) {
		this.member = member;
	}
}
