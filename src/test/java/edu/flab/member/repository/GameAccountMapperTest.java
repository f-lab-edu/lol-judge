package edu.flab.member.repository;

import static edu.flab.member.domain.LolTier.Color.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.*;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import edu.flab.member.domain.GameAccount;
import edu.flab.member.domain.LolTier;
import edu.flab.member.domain.LolTierUtil;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.GameAccountUpdateDto;

@MybatisTest
@AutoConfigureTestDatabase(replace = NONE)
class GameAccountMapperTest {

	@Autowired
	private GameAccountMapper sut;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private SqlSession sqlSession;

	private final LolTier challenger = LolTierUtil.createHighTier(CHALLENGER, 1000);

	// given
	private final GameAccount gameAccount = GameAccount.builder()
		.lolLoginId("login12345")
		.memberId(1L)
		.nickname("hide on bush")
		.lolTier(challenger)
		.build();

	private final Member member = Member.builder()
		.email("user123@email.com")
		.password("1234")
		.profileUrl("abcd")
		.gameAccount(gameAccount)
		.build();

	@BeforeEach
	void clean() throws SQLException {
		this.sqlSession.getConnection().prepareStatement("ALTER TABLE member AUTO_INCREMENT = 1").execute();
	}

	@Test
	@DisplayName("데이터베이스에 저장된 GameAccount 정보를 ID 값을 통해 조회할 수 있다")
	void 아이디_조회() {
		memberMapper.save(member);

		sut.save(gameAccount);

		GameAccount findGameAccount = sut.findById(gameAccount.getId()).orElseThrow();

		assertThat(findGameAccount).isEqualTo(gameAccount);
	}

	@Test
	@DisplayName("데이터베이스에 저장된 GameAccount 정보를 롤계정 아이디를 통해 조회할 수 있다")
	void 롤계정_조회() {
		memberMapper.save(member);

		sut.save(gameAccount);

		GameAccount findGameAccount = sut.findByLoginId(gameAccount.getLolLoginId()).orElseThrow();

		assertThat(findGameAccount).isEqualTo(gameAccount);
	}

	@Test
	@DisplayName("데이터베이스에 GameAccount 정보의 닉네임, 계정 아이디 변경을 요청하면 데이터가 변경된다")
	void 수정() {
		memberMapper.save(member);

		sut.save(gameAccount);

		GameAccountUpdateDto dto = GameAccountUpdateDto.builder()
			.id(gameAccount.getId())
			.lolLoginId("newLogin1234")
			.nickname("show maker")
			.lolTier(challenger)
			.build();

		sut.update(dto);

		GameAccount findGameAccount = sut.findById(gameAccount.getId()).orElseThrow();
		assertThat(dto).usingRecursiveComparison().isEqualTo(findGameAccount);
	}
}
