package edu.flab.member.api;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import edu.flab.member.dto.RiotApiLeagueEntryResponseDto;
import edu.flab.member.dto.RiotApiSummonerInfoResponseDto;

public interface RiotHttpApiClient {

	@GetExchange("/lol/league/v4/entries/by-summoner/{encryptedSummonerId}")
	List<RiotApiLeagueEntryResponseDto> getLeagueEntryDto(@PathVariable String encryptedSummonerId);

	@GetExchange("/lol/summoner/v4/summoners/by-name/{summonerName}")
	RiotApiSummonerInfoResponseDto getSummonerInfo(@PathVariable String summonerName);
}
