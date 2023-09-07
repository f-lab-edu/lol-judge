package edu.flab.election.domain;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import edu.flab.election.config.ElectionConstant;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
public class Election {
	private Long id;

	@NotNull
	private ElectionStatus status;

	@Length(max = 50)
	private String youtubeUrl;

	@Range(min = ElectionConstant.MIN_COST, max = ElectionConstant.MAX_COST)
	private int cost;

	@PositiveOrZero
	private long totalVotedCount;

	@Range(min = ElectionConstant.MIN_PROGRESS_HOUR, max = ElectionConstant.MAX_PROGRESS_HOUR)
	private int progressTime;

	@NotNull
	private OffsetDateTime createdAt;

	@NotNull
	private OffsetDateTime endedAt;

	List<Candidate> candidates;

	public void addCandidate(Candidate candidate) {
		if (candidates == null) {
			candidates = new ArrayList<>();
		}
		candidates.add(candidate);
		candidate.setElectionId(id);
	}

	public Candidate getHost() {
		return getCandidates(0);
	}

	public Candidate getParticipant() {
		return getCandidates(1);
	}

	private Candidate getCandidates(int index) {
		if (index < candidates.size()) {
			return candidates.get(index);
		}
		throw new NoSuchElementException("선거의 후보자를 조회했으나 존재하지 않습니다.");
	}
}
