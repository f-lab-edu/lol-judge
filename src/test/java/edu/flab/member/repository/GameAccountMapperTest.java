package edu.flab.member.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import edu.flab.global.vo.RankTier;
import edu.flab.member.domain.GameAccount;
import edu.flab.member.dto.GameAccountUpdateDto;

@MybatisTest
class GameAccountMapperTest {

	@Autowired
	private GameAccountMapper sut;

	@Test
	@DisplayName("데이터베이스에 GameAccount 저장을 요청하면 데이터베이스에 보관되고, ID 값을 통해 조회할 수 있다")
	void 저장_조회() {

		// given
		GameAccount gameAccount = GameAccount.builder()
			.loginId("login12345")
			.nickname("hide on bush")
			.rankTier(RankTier.CHALLENGER)
			.build();

		// when
		sut.save(gameAccount);

		// then
		GameAccount findGameAccount = sut.findById(gameAccount.getId()).orElseThrow();
		assertThat(findGameAccount).isEqualTo(gameAccount);
	}

	@Test
	@DisplayName("데이터베이스에 GameAccount 수정을 요청하면 튜플 데이터가 변경된다")
	void 수정() {

		// given
		GameAccount gameAccount = GameAccount.builder()
			.loginId("login1234")
			.nickname("hide on bush")
			.rankTier(RankTier.CHALLENGER)
			.build();

		sut.save(gameAccount);

		// when
		GameAccountUpdateDto dto = GameAccountUpdateDto.builder()
			.loginId("newLogin1234")
			.nickname("show maker")
			.rankTier(RankTier.CHALLENGER)
			.build();

		Long id = gameAccount.getId();

		sut.update(id, dto);

		// // then
		GameAccount findGameAccount = sut.findById(id).orElseThrow();
		assertThat(dto).usingRecursiveComparison().isEqualTo(findGameAccount);
	}

	@Test
	@DisplayName("데이터베이스에 loginId 중복 검사를 요청했을 때, 중복된 loginId 가 있다면 true 를, 없다면 false 를 반환한다")
	void 중복체크() {

		// given
		GameAccount gameAccount = GameAccount.builder()
			.loginId("login1234")
			.nickname("hide on bush")
			.rankTier(RankTier.CHALLENGER)
			.build();

		sut.save(gameAccount);

		// when
		boolean duplicated1 = sut.isDuplicated("login1234");
		boolean duplicated2 = sut.isDuplicated("no1234");

		// then
		assertThat(duplicated1).isTrue();
		assertThat(duplicated2).isFalse();
	}
}
