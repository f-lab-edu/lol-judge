package edu.flab.member.dto;

import edu.flab.member.domain.LolTier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RiotSummonerInfoDto {
	private String summonerName;

	private String encryptedSummonerId;

	private LolTier lolTier;
}
