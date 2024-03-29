package edu.flab.election.dto;

import java.time.OffsetDateTime;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ElectionDetailResponseDto {
	private Long id;

	@Length(max = 50)
	private String youtubeUrl;

	@NotBlank
	private String title;

	@NotBlank
	private String writer;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy", timezone = "Asia/Seoul")
	private OffsetDateTime createdAt;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy", timezone = "Asia/Seoul")
	private OffsetDateTime endedAt;

	private long totalVotedCount;

	private List<CandidateDetailDto> candidateDetails;
}
