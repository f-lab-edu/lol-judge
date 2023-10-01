package edu.flab.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RiotApiSummonerInfoResponseDto {
	private String id;

	private String accountId;

	private String puuid;

	private String name;

	private int profileIconId;

	private long revisionDate;

	private long summonerLevel;
}
