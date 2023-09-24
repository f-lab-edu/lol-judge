package edu.flab.election.dto;

import java.time.format.DateTimeFormatter;

import edu.flab.election.domain.Election;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ElectionInfoDto {
	private Long id;
	private String thumbnail;
	private String title;
	private Long totalVotedCount;
	private String createdAt;
	private String writer;

	public ElectionInfoDto(Election election) {
		this.id = election.getId();
		this.thumbnail = election.getThumbnailUrl();
		this.totalVotedCount = election.getTotalVotedCount();
		this.createdAt = election.getCreatedAt().format(DateTimeFormatter.ISO_DATE);
		this.title = election.getTitle();
		this.writer = election.getMember().getGameAccount().getNickname();
	}
}
