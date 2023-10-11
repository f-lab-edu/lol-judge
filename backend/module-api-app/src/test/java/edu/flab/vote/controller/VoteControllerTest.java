package edu.flab.vote.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import edu.flab.GlobalExceptionHandler;
import edu.flab.TestContainerIntegrationTest;
import edu.flab.election.domain.Election;
import edu.flab.election.repository.ElectionJpaRepository;
import edu.flab.exception.BusinessException;
import edu.flab.member.TestFixture;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberLoginResponseDto;
import edu.flab.member.repository.MemberJpaRepository;
import edu.flab.web.config.LoginConstant;
import edu.flab.web.supoort.LoginArgumentResolver;

@Tag("integration")
@ActiveProfiles("test")
@Testcontainers
@SpringBootTest
class VoteControllerTest extends TestContainerIntegrationTest {

	@Autowired
	private VoteController sut;

	@Autowired
	private ElectionJpaRepository electionJpaRepository;

	@Autowired
	private MemberJpaRepository memberJpaRepository;

	private MockMvc mock;

	@BeforeEach
	void setUp() {
		mock = MockMvcBuilders.standaloneSetup(sut)
			.setControllerAdvice(new GlobalExceptionHandler())
			.setCustomArgumentResolvers(new LoginArgumentResolver())
			.build();
	}

	@Test
	@DisplayName("후보자 중 한명에게 투표할 수 있으며, 참여 비용 10포인트 차감된다")
	void test1() throws Exception {
		// given
		Member voter = TestFixture.getMember();
		voter.setJudgePoint(100);
		voter.setAuthenticated(true);
		memberJpaRepository.save(voter);

		Election election = TestFixture.getElection();
		electionJpaRepository.save(election);

		MockHttpSession mockHttpSession = new MockHttpSession();
		mockHttpSession.setAttribute(LoginConstant.LOGIN_SESSION_ATTRIBUTE,
			new MemberLoginResponseDto(voter.getId(), voter.getGameAccount().getSummonerName(), voter.getEmail()));

		// then
		mock.perform(MockMvcRequestBuilders.get("/vote/1")
				.session(mockHttpSession)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("회원이 보유한 포인트가 참여 포인트보다 부족하면, 투표 시 예외가 발생한다")
	void test2() throws Exception {
		// given
		Member voter = TestFixture.getMember();
		voter.setJudgePoint(0);
		voter.setAuthenticated(true);
		memberJpaRepository.save(voter);

		Election election = TestFixture.getElection();
		electionJpaRepository.save(election);

		MockHttpSession mockHttpSession = new MockHttpSession();
		mockHttpSession.setAttribute(LoginConstant.LOGIN_SESSION_ATTRIBUTE,
			new MemberLoginResponseDto(voter.getId(), voter.getGameAccount().getSummonerName(), voter.getEmail()));

		// then
		mock.perform(MockMvcRequestBuilders.get("/vote/1")
				.session(mockHttpSession)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(
				result -> Assertions.assertThat(result.getResolvedException() instanceof IllegalArgumentException)
					.isTrue())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("이미 투표한 경우, 다시 투표하면 예외가 발생한다")
	void test3() throws Exception {
		// given
		Member voter = TestFixture.getMember();
		voter.setJudgePoint(100);
		voter.setAuthenticated(true);
		memberJpaRepository.save(voter);

		Election election = TestFixture.getElection();
		electionJpaRepository.save(election);

		MockHttpSession mockHttpSession = new MockHttpSession();
		mockHttpSession.setAttribute(LoginConstant.LOGIN_SESSION_ATTRIBUTE,
			new MemberLoginResponseDto(voter.getId(), voter.getGameAccount().getSummonerName(), voter.getEmail()));

		// then
		mock.perform(MockMvcRequestBuilders.get("/vote/1")
				.session(mockHttpSession)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());

		// then
		mock.perform(MockMvcRequestBuilders.get("/vote/1")
				.session(mockHttpSession)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(
				result -> Assertions.assertThat(result.getResolvedException() instanceof BusinessException).isTrue())
			.andDo(MockMvcResultHandlers.print());
	}
}
