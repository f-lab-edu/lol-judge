package edu.flab.member;

import static edu.flab.member.domain.LolTier.Color.*;

import java.time.OffsetDateTime;

import com.github.javafaker.Faker;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Election;
import edu.flab.election.domain.Opinion;
import edu.flab.member.domain.GameAccount;
import edu.flab.member.domain.LolTier;
import edu.flab.member.domain.LolTierUtil;
import edu.flab.member.domain.Member;

public class TestFixture {

	public static final Faker faker = Faker.instance();
	public static Long memberId = 0L;
	public static Long gameAccountId = 0L;
	public static Long electionId = 0L;
	public static Long candidateId = 0L;

	public static Member getMember() {
		GameAccount gameAccount = GameAccount.builder()
			.id(gameAccountId++)
			.summonerName("guest" + faker.number().digits(10))
			.encryptedId("id" + faker.number().digits(10))
			.lolTier(LolTierUtil.createTier(CHALLENGER, LolTier.Level.I, 1000))
			.build();

		return Member.builder()
			.id(memberId++)
			.email(faker.internet().emailAddress())
			.password("passWORD1234!")
			.profileUrl("example-cloud.storage/profile.jpg")
			.gameAccount(gameAccount)
			.build();
	}

	public static Election getElection() {
		Member writer = getMember();

		Candidate candidate1 = Candidate.builder()
			.id(candidateId++)
			.opinion(new Opinion("쉬바나", "쌍둥이 포탑을 쳤으면 게임을 끝냈다"))
			.build();

		Candidate candidate2 = Candidate.builder()
			.id(candidateId++)
			.opinion(new Opinion("판테온", "시간이 부족해서 끝낼 수 없었다"))
			.build();

		Election election = Election.builder()
			.id(electionId++)
			.youtubeUrl("https://youtube.com/live/eLlxrBmD3H4")
			.progressTime(72)
			.createdAt(OffsetDateTime.now())
			.endedAt(OffsetDateTime.now().plusHours(72))
			.build();

		election.addCandidate(candidate1);
		election.addCandidate(candidate2);
		writer.addElection(election);

		return election;
	}
}

