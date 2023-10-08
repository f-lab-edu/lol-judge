package edu.flab.member.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.flab.GlobalExceptionHandler;
import edu.flab.TestContainerIntegrationTest;
import edu.flab.member.TestFixture;
import edu.flab.member.api.RiotHttpApiClient;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberLoginRequestDto;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.member.dto.RiotApiLeagueEntryResponseDto;
import edu.flab.member.dto.RiotApiSummonerInfoResponseDto;
import edu.flab.util.MailAuthCodeGenerator;
import edu.flab.web.supoort.LoginArgumentResolver;

class MemberControllerTest extends TestContainerIntegrationTest {

	@Autowired
	private MemberController sut;

	@Autowired
	private ObjectMapper objectMapper;

	@SpyBean
	private MailAuthCodeGenerator mailAuthCodeGenerator;

	private MockMvc mock;

	private final CountDownLatch countDownLatch = new CountDownLatch(2);

	@BeforeEach
	void setUp() {
		mock = MockMvcBuilders.standaloneSetup(sut)
			.setControllerAdvice(new GlobalExceptionHandler())
			.setCustomArgumentResolvers(new LoginArgumentResolver())
			.build();
	}

	/**
	 * test fixture
	 */
	private final Member member = TestFixture.getMember();

	private final MemberSignUpDto signUpDto = MemberSignUpDto.builder()
		.email(member.getEmail())
		.password(member.getPassword())
		.summonerName(member.getGameAccount().getSummonerName())
		.position("MID")
		.build();

	private final MemberLoginRequestDto loginDto = MemberLoginRequestDto.builder()
		.email(signUpDto.getEmail())
		.password(signUpDto.getPassword())
		.build();

	@Test
	@DisplayName("회원가입에 성공한다")
	void test1() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpDto)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("비밀번호 규칙을 어겨서 회원가입에 실패한다")
	void test2() throws Exception {
		MemberSignUpDto pwdWrongSignUpDto = MemberSignUpDto.builder()
			.email("user123")
			.password("pwd1234")
			.summonerName("킹메준")
			.position("MID")
			.build();

		mock.perform(MockMvcRequestBuilders.post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(pwdWrongSignUpDto)))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("회원가입 이후 메일 인증을 하지 않으면 로그인에 실패한다")
	void test3() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpDto)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());

		mock.perform(MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDto)))
			.andExpect(result -> Assertions.assertThat(result.getResolvedException() instanceof NoSuchElementException).isTrue())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("회원가입 - 메일 인증을 거친 후 로그인에 성공한다")
	void test4() throws Exception {
		Mockito.when(mailAuthCodeGenerator.generateAuthCode()).thenReturn("0000");

		mock.perform(MockMvcRequestBuilders.post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpDto)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());

		countDownLatch.await(1, TimeUnit.SECONDS);

		mock.perform(MockMvcRequestBuilders.get("/auth-agree/0000"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andDo(MockMvcResultHandlers.print());

		mock.perform(MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDto)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("회원가입 이후 비밀번호를 틀려 로그인에 실패한다")
	void test5() throws Exception {
		MemberLoginRequestDto wrongPasswordDto = loginDto;
		wrongPasswordDto.setPassword("WrongPassword");

		mock.perform(MockMvcRequestBuilders.post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpDto)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());

		mock.perform(MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(wrongPasswordDto)))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("존재하지않는 이메일를 입력하여 로그인에 실패한다")
	void test6() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDto)))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andDo(MockMvcResultHandlers.print());
	}

	@TestConfiguration
	static class testConfig {
		@Bean
		@Primary
		public RiotHttpApiClient riotApi() {
			return new RiotHttpApiClient() {
				@Override
				public List<RiotApiLeagueEntryResponseDto> getLeagueEntryDto(String encryptedSummonerId) {
					return List.of(RiotApiLeagueEntryResponseDto.builder()
						.leagueId("6a026825-9531-4c17-9228-7bc3cb7375a5")
						.queueType("RANKED_SOLO_5x5")
						.tier("GOLD")
						.rank("III")
						.leaguePoints(0)
						.wins(3)
						.losses(3)
						.veteran(false)
						.inactive(false)
						.freshBlood(false)
						.hotStreak(false)
						.build());
				}

				@Override
				public RiotApiSummonerInfoResponseDto getSummonerInfo(String summonerName) {
					return RiotApiSummonerInfoResponseDto.builder()
						.id("encryptedId")
						.accountId("accountId")
						.puuid("puuid")
						.name("킹메준")
						.profileIconId(5227)
						.revisionDate(1696667197000L)
						.summonerLevel(57)
						.build();
				}
			};
		}
	}
}
