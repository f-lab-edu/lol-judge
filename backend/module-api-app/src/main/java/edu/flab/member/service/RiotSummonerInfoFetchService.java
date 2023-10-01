package edu.flab.member.service;

import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import edu.flab.exception.BusinessException;
import edu.flab.member.api.RiotHttpApiClient;
import edu.flab.member.domain.LolTier;
import edu.flab.member.domain.LolTierUtil;
import edu.flab.member.dto.RiotSummonerInfoDto;
import edu.flab.web.response.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class RiotSummonerInfoFetchService {

	public static final String SOLO_QUEUE = "RANKED_SOLO_5x5";

	private final RiotHttpApiClient client;

	@Retryable(maxAttempts = 2, backoff = @Backoff(1000))
	public RiotSummonerInfoDto fetchSummonerRankInfo(String summonerName) {
		try {
			String summonerId = client.getSummonerInfo(summonerName).getId();

			LolTier summonerRankTier = fetchSummonerRankTier(summonerId);

			return new RiotSummonerInfoDto(summonerName, summonerId, summonerRankTier);

		} catch (WebClientResponseException e) {
			if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
				log.error("Riot API 연결에 문제가 발생하였습니다.");
				throw new BusinessException(ErrorCode.SUMMONER_NOT_FOUND, e);
			}
			throw new BusinessException(ErrorCode.RIOT_SERVER_ERROR, e);
		}
	}

	private LolTier fetchSummonerRankTier(String summonerId) {
		return client.getLeagueEntryDto(summonerId)
			.stream()
			.filter(x -> x.getQueueType().equals(SOLO_QUEUE))
			.map(x -> LolTierUtil.createTier(x.getTier(), x.getRank(), x.getLeaguePoints()))
			.findFirst()
			.orElse(LolTierUtil.createUnRankTier());
	}
}
