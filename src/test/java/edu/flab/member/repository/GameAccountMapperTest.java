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
	@DisplayName("데이터베이스에 저장된 GameAccount 정보를 ID 값을 통해 조회할 수 있다")
	void 아이디_조회() {
		// given
		GameAccount gameAccount = GameAccount.builder()
			.loginId("login12345")
			.nickname("hide on bush")
			.rankTier(RankTier.CHALLENGER)
			.build();

		// when
		sut.save(gameAccount);
		GameAccount findGameAccount = sut.findById(gameAccount.getId()).orElseThrow();

		// then
		assertThat(findGameAccount).isEqualTo(gameAccount);
	}

	@Test
	@DisplayName("데이터베이스에 저장된 GameAccount 정보를 롤계정 아이디를 통해 조회할 수 있다")
	void 롤계정_조회() {
		// given
		GameAccount gameAccount = GameAccount.builder()
			.loginId("login1234")
			.nickname("hide on bush")
			.rankTier(RankTier.CHALLENGER)
			.build();

		// when
		sut.save(gameAccount);
		GameAccount findGameAccount = sut.findByLoginId(gameAccount.getLoginId()).orElseThrow();

		// then
		assertThat(findGameAccount).isEqualTo(gameAccount);
	}

	@Test
	@DisplayName("데이터베이스에 GameAccount 정보의 닉네임, 계정 아이디 변경을 요청하면 데이터가 변경된다")
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
			.id(gameAccount.getId())
			.loginId("newLogin1234")
			.nickname("show maker")
			.rankTier(RankTier.CHALLENGER)
			.build();

		sut.update(dto);

		// // then
		GameAccount findGameAccount = sut.findById(gameAccount.getId()).orElseThrow();
		assertThat(dto).usingRecursiveComparison().isEqualTo(findGameAccount);
	}
}
