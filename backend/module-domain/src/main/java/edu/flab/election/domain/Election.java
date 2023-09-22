package edu.flab.election.domain;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import edu.flab.election.config.ElectionRule;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	@Default
	@OneToMany(mappedBy = "election")
	List<Candidate> candidates = new ArrayList<>();

	@Default
	@Enumerated(EnumType.STRING)
	private ElectionStatus status = ElectionStatus.PENDING;

	@URL
	@Length(max = 50)
	private String youtubeUrl;

	@Range(min = ElectionRule.MIN_COST, max = ElectionRule.MAX_COST)
	private int cost;

	@PositiveOrZero
	private long totalVotedCount;

	@Range(min = ElectionRule.MIN_PROGRESS_HOUR, max = ElectionRule.MAX_PROGRESS_HOUR)
	private int progressTime;

	private OffsetDateTime createdAt;

	private OffsetDateTime endedAt;

	private boolean deleted;

	public String getTitle() {
		Candidate host = getCandidate(CandidateStatus.HOST);
		Candidate participant = getCandidate(CandidateStatus.PARTICIPANT);
		return host.getChampion() + ":" + host.getOpinion() + " vs " +
			participant.getChampion() + ":" + participant.getOpinion();
	}

	public Candidate getCandidate(CandidateStatus candidateStatus) {
		return candidates.stream()
			.filter(c -> c.getCandidateStatus() == candidateStatus)
			.findFirst()
			.orElseThrow(() -> new NoSuchElementException("선거의 후보자를 조회했으나 존재하지 않습니다."));
	}

	public Election changeContents(String youtubeUrl, int cost) {
		this.youtubeUrl = youtubeUrl;
		this.cost = cost;
		return this;
	}

	public Election changeStatus(ElectionStatus status) {
		this.status = status;
		return this;
	}
}
