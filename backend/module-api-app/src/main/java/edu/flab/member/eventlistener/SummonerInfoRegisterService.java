package edu.flab.member.eventlistener;

import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import edu.flab.election.repository.GameAccountJpaRepository;
import edu.flab.exception.BusinessException;
import edu.flab.member.domain.GameAccount;
import edu.flab.member.domain.GamePosition;
import edu.flab.member.dto.RiotSummonerInfoDto;
import edu.flab.member.event.MemberSignUpEvent;
import edu.flab.member.service.RiotSummonerInfoFetchService;
import edu.flab.web.response.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SummonerInfoRegisterService {

	private final RiotSummonerInfoFetchService riotSummonerInfoFetchService;
	private final GameAccountJpaRepository gameAccountJpaRepository;

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void handle(MemberSignUpEvent event) {
		RiotSummonerInfoDto summonerInfo = validateSummonerName(event.summonerName());

		GameAccount gameAccount = GameAccount.builder()
			.encryptedId(summonerInfo.getEncryptedSummonerId())
			.summonerName(summonerInfo.getSummonerName())
			.lolTier(summonerInfo.getLolTier())
			.position(GamePosition.of(event.gamePosition()))
			.build();

		event.member().setGameAccount(gameAccount);
	}

	private RiotSummonerInfoDto validateSummonerName(String summonerName) {
		if (gameAccountJpaRepository.existsBySummonerName(summonerName)) {
			throw new BusinessException(ErrorCode.DUPLICATE_SUMMONER_NAME);
		}
		return riotSummonerInfoFetchService.fetchSummonerRankInfo(summonerName);
	}
}
