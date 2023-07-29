package edu.flab.election.domain;

import jakarta.validation.constraints.NotBlank;
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

	@NotBlank
	private String contents;

	@NotNull
	private VotedStatus votedStatus;
}
