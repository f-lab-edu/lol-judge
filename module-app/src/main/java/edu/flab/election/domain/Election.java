package edu.flab.election.domain;

import java.time.OffsetDateTime;
import java.util.List;

import org.hibernate.validator.constraints.Range;

import edu.flab.election.config.ElectionConstant;
import jakarta.validation.constraints.NotBlank;
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
	public enum ElectionStatus {
		PENDING,                    // 당사자 간 내용 합의 대기 중
		PENDING_HOST_NCK,           // 호스트측이 내용 합의 거절
		PENDING_PARTICIPANT_NCK,    // 대상이 합의 거절
		IN_PROGRESS,                // 재판 진행 중
		FINISHED                    // 재판 판결 완료
	}

	private Long id;

	@NotNull
	private ElectionStatus status;

	@NotBlank
	private String contents;

	@NotBlank
	private String youtubeUrl;

	@Range(min = ElectionConstant.MIN_COST, max = ElectionConstant.MAX_COST)
	private int cost;

	@PositiveOrZero
	private long totalVotedCount;

	@NotNull
	private OffsetDateTime createdAt;

	@NotNull
	private OffsetDateTime endedAt;

	List<Candidate> candidates;
}
