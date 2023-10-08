package edu.flab.member;

import static edu.flab.member.domain.LolTier.Color.*;

import java.time.OffsetDateTime;

import com.github.javafaker.Faker;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Election;
import edu.flab.election.domain.Opinion;
import edu.flab.election.domain.VotedStatus;
import edu.flab.member.domain.GameAccount;
import edu.flab.member.domain.LolTier;
import edu.flab.member.domain.LolTierUtil;
import edu.flab.member.domain.Member;

public class TestFixture {

	public static final Faker faker = Faker.instance();

	public static Member getMember(LolTier lolTier) {
		GameAccount gameAccount = GameAccount.builder()
			.summonerName("guest" + faker.number().digits(10))
			.encryptedId("id" + faker.number().digits(10))
			.lolTier(lolTier)
			.build();

		return Member.builder()
			.email(faker.internet().emailAddress())
			.password("passWORD1234!")
			.profileUrl("https://example-cloud.storage/profile.jpg")
			.gameAccount(gameAccount)
			.build();
	}

	public static Member getMember() {
		return getMember(LolTierUtil.createTier(CHALLENGER, LolTier.Level.I, 1000));
	}

	public static Election getElection() {
		Member writer = getMember();

		Candidate candidate1 = Candidate.builder()
			.opinion(new Opinion("쉬바나", "쌍둥이 포탑을 쳤으면 게임을 끝냈다"))
			.votedStatus(VotedStatus.UNKNOWN)
			.build();

		Candidate candidate2 = Candidate.builder()
			.opinion(new Opinion("판테온", "시간이 부족해서 끝낼 수 없었다"))
			.votedStatus(VotedStatus.UNKNOWN)
			.build();

		Election election = Election.builder()
			.title("누가 잘못했나요?")
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

	public static Candidate getCandidate() {
		return getElection().getCandidates().get(0);
	}
}

