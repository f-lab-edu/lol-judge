package edu.flab.member;

import static edu.flab.member.domain.LolTier.Color.*;

import edu.flab.member.domain.GameAccount;
import edu.flab.member.domain.LolTierUtil;
import edu.flab.member.domain.Member;

public class TestFixture {

	public static Member getMember() {
		GameAccount gameAccount = GameAccount.builder()
			.lolId("login12345")
			.nickname("hide on bush")
			.lolTier(LolTierUtil.createHighTier(CHALLENGER, 1000))
			.build();

		return Member.builder()
			.email("user123@email.com")
			.password("passWORD1234!")
			.profileUrl("example-cloud.storage/profile.jpg")
			.gameAccount(gameAccount)
			.build();
	}
}

