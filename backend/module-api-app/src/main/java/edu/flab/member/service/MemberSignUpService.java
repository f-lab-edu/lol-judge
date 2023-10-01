package edu.flab.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.flab.election.repository.GameAccountJpaRepository;
import edu.flab.exception.BusinessException;
import edu.flab.member.domain.GameAccount;
import edu.flab.member.domain.GamePosition;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.member.dto.RiotSummonerInfoDto;
import edu.flab.member.repository.MemberJpaRepository;
import edu.flab.web.response.ErrorCode;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberSignUpService {
	private final MemberJpaRepository memberJpaRepository;
	private final GameAccountJpaRepository gameAccountJpaRepository;
	private final PasswordEncoder passwordEncoder;
	private final RiotSummonerInfoFetchService riotSummonerInfoFetchService;

	@Transactional
	public Member signUp(MemberSignUpDto dto) {
		String email = validateEmail(dto.getEmail());
		RiotSummonerInfoDto summonerInfo = validateSummonerName(dto.getSummonerName());

		Member member = Member.builder()
			.email(email)
			.password(encryptPassword(dto.getPassword()))
			.profileUrl(dto.getProfileUrl())
			.build();

		GameAccount gameAccount = GameAccount.builder()
			.encryptedId(summonerInfo.getEncryptedSummonerId())
			.summonerName(summonerInfo.getSummonerName())
			.lolTier(summonerInfo.getLolTier())
			.position(GamePosition.of(dto.getPosition()))
			.build();

		member.setGameAccount(gameAccount);

		return memberJpaRepository.save(member);
	}

	private RiotSummonerInfoDto validateSummonerName(String summonerName) {
		if (gameAccountJpaRepository.existsBySummonerName(summonerName)) {
			throw new BusinessException(ErrorCode.DUPLICATE_SUMMONER_NAME);
		}
		return riotSummonerInfoFetchService.fetchSummonerRankInfo(summonerName);
	}

	private String validateEmail(@Email String email) {
		if (memberJpaRepository.existsByEmail(email)) {
			throw new BusinessException(ErrorCode.DUPLICATE_ACCOUNT);
		}
		return email;
	}

	private String encryptPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
