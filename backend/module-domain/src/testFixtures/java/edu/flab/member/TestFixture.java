package edu.flab.member;

import static edu.flab.member.domain.LolTier.Color.*;

import java.time.OffsetDateTime;

import com.github.javafaker.Faker;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.CandidateStatus;
import edu.flab.election.domain.Election;
import edu.flab.member.domain.GameAccount;
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
			.lolId("guest" + faker.number().digits(10))
			.nickname("user" + faker.number().digits(10))
			.lolTier(LolTierUtil.createHighTier(CHALLENGER, 1000))
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
		Candidate host = Candidate.builder()
			.id(candidateId++)
			.candidateStatus(CandidateStatus.HOST)
			.opinion("쌍둥이 포탑을 쳤으면 게임을 끝냈다")
			.champion(faker.leagueOfLegends().champion())
			.build();

		Candidate participant = Candidate.builder()
			.id(candidateId++)
			.candidateStatus(CandidateStatus.PARTICIPANT)
			.opinion("시간이 부족해서 끝낼 수 없었다")
			.champion(faker.leagueOfLegends().champion())
			.build();

		Election election = Election.builder()
			.id(electionId++)
			.cost(100)
			.youtubeUrl("https://youtube.com/example")
			.progressTime(72)
			.createdAt(OffsetDateTime.now())
			.endedAt(OffsetDateTime.now().plusHours(72))
			.build();

		host.setMember(getMember());
		host.setElection(election);
		participant.setMember(getMember());
		participant.setElection(election);

		return election;
	}
}

