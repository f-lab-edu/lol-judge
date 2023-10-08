package edu.flab.election.controller;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.flab.GlobalExceptionHandler;
import edu.flab.TestContainerIntegrationTest;
import edu.flab.election.domain.Opinion;
import edu.flab.election.dto.ElectionRegisterRequestDto;
import edu.flab.election.dto.ElectionRegisterResponseDto;
import edu.flab.election.service.ElectionFindService;
import edu.flab.member.TestFixture;
import edu.flab.member.api.RiotHttpApiClient;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberLoginResponseDto;
import edu.flab.member.dto.RiotApiLeagueEntryResponseDto;
import edu.flab.member.dto.RiotApiSummonerInfoResponseDto;
import edu.flab.member.repository.MemberJpaRepository;
import edu.flab.web.config.LoginConstant;
import edu.flab.web.response.SuccessResponse;
import edu.flab.web.supoort.LoginArgumentResolver;

class ElectionControllerTest extends TestContainerIntegrationTest {

	@Autowired
	private ElectionController electionController;

	@Autowired
	private MemberJpaRepository memberJpaRepository;

	@Autowired
	private ElectionFindService electionFindService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private MockMvc mock;

	@BeforeEach
	void setUpMockMvc() {
		mock = MockMvcBuilders.standaloneSetup(electionController)
			.setControllerAdvice(new GlobalExceptionHandler())
			.setCustomArgumentResolvers(new LoginArgumentResolver())
			.build();
	}

	@Test
	@DisplayName("회원이 재판을 신청하면, 데이터베이스에 Election 레코드가 저장된다")
	void test1() throws Exception {
		// given
		Member writer = TestFixture.getMember();
		writer.setAuthenticated(true);
		memberJpaRepository.save(writer);

		MockHttpSession mockHttpSession = new MockHttpSession();
		mockHttpSession.setAttribute(LoginConstant.LOGIN_SESSION_ATTRIBUTE,
			new MemberLoginResponseDto(writer.getId(), writer.getGameAccount().getSummonerName(), writer.getEmail()));

		// when
		ElectionRegisterRequestDto electionAddDto = ElectionRegisterRequestDto.builder()
			.title("원딜이 쌍둥이 포탑을 계속 쳤으면 게임을 끝낼 수 있었다 vs 카밀의 압박 때문에 할 수 없다")
			.youtubeUrl("https://www.youtube.com/shorts/fkzDsJzIUZs")
			.progressTime(60)
			.opinions(List.of(new Opinion("쉬바나", "원딜이 쌍둥이 포탑을 계속 쳤으면 게임 끝냈다"), new Opinion("시비르", "카밀 압박 때문에 할 수 없다")))
			.build();

		MvcResult mvcResult = mock.perform(MockMvcRequestBuilders.post("/elections")
				.session(mockHttpSession)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(electionAddDto)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();

		// then
		JavaType javaType = objectMapper.getTypeFactory()
			.constructParametricType(SuccessResponse.class, ElectionRegisterResponseDto.class);

		SuccessResponse<ElectionRegisterResponseDto> result = objectMapper.readValue(
			mvcResult.getResponse().getContentAsString(), javaType);

		Assertions.assertThatNoException()
			.isThrownBy(() -> electionFindService.findElection(result.data().getElectionId()));
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
