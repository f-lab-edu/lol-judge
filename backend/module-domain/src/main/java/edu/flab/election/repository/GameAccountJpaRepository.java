package edu.flab.election.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.flab.member.domain.GameAccount;

public interface GameAccountJpaRepository extends JpaRepository<GameAccount, Long> {
	boolean existsBySummonerName(String summonerName);
}
