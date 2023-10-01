package edu.flab.election.controller;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.flab.election.domain.Opinion;
import edu.flab.election.dto.ElectionRegisterRequestDto;
import edu.flab.election.dto.ElectionRegisterResponseDto;
import edu.flab.election.service.ElectionFindService;
import edu.flab.member.controller.MemberController;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberLoginResponseDto;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.member.service.MemberSignUpService;
import edu.flab.web.config.LoginConstant;
import edu.flab.GlobalExceptionHandler;
import edu.flab.web.response.SuccessResponse;
import edu.flab.web.supoort.LoginArgumentResolver;

@Tag("integration")
@Transactional
@Testcontainers
@SpringBootTest
class ElectionControllerTest {

	@Autowired
	private ElectionController electionController;

	@Autowired
	private ElectionFindService electionFindService;

	@Autowired
	private MemberController memberController;

	@Autowired
	private MemberSignUpService memberSignUpService;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mock;

	private ClientAndServer mockServer;

	@Container
	private static final GenericContainer redis = new GenericContainer(
		DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);

	@Container
	private static final RabbitMQContainer rabbitmq = new RabbitMQContainer("rabbitmq:latest").withExposedPorts(5672,
		15672);

	@BeforeEach
	void setUp() {
		mockServer = ClientAndServer.startClientAndServer(443);
		redis.start();
		rabbitmq.start();
		mock = MockMvcBuilders.standaloneSetup(memberController, electionController)
			.setControllerAdvice(new GlobalExceptionHandler())
			.setCustomArgumentResolvers(new LoginArgumentResolver())
			.build();
	}

	@AfterEach
	void shutDown() {
		mockServer.stop();
	}

	@DynamicPropertySource
	static void registerProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.rabbitmq.host", rabbitmq::getHost);
		registry.add("spring.rabbitmq.port", rabbitmq::getAmqpPort);
		registry.add("spring.rabbitmq.username", rabbitmq::getAdminUsername);
		registry.add("spring.rabbitmq.password", rabbitmq::getAdminPassword);
	}

	@Test
	@DisplayName("로그인 회원은 재판을 신청할 수 있고, 관련자에게 메시지가 전송된다")
	void test1() throws Exception {
		// given
		MemberSignUpDto writerSignUpDto = MemberSignUpDto.builder()
			.email("host@example.com")
			.password("aB#12345")
			.summonerName("lolId1111")
			.build();

		Member writer = memberSignUpService.signUp(writerSignUpDto);

		ElectionRegisterRequestDto electionAddDto = ElectionRegisterRequestDto.builder()
			.title("원딜이 쌍둥이 포탑을 계속 쳤으면 게임을 끝낼 수 있었다 vs 카밀의 압박 때문에 할 수 없다")
			.youtubeUrl("https://youtube.com/7I5SKTY-JXc")
			.progressTime(60)
			.opinions(List.of(new Opinion("쉬바나", "원딜이 쌍둥이 포탑을 계속 쳤으면 게임 끝냈다"), new Opinion("시비르", "카밀 압박 때문에 할 수 없다")))
			.build();

		MockHttpSession mockHttpSession = new MockHttpSession();
		mockHttpSession.setAttribute(LoginConstant.LOGIN_SESSION_ATTRIBUTE,
			new MemberLoginResponseDto(writer.getId(), writerSignUpDto.getSummonerName(), writer.getEmail()));

		new MockServerClient("kr.api.riotgames.com", 443).when(HttpRequest.request()
				.withMethod(HttpMethod.GET.name())
				.withPath("/lol/summoner/v4/summoners/by-name/" + writerSignUpDto.getSummonerName()))
			.respond(HttpResponse.response().withStatusCode(200).withBody("AA"));

		// // riot api server mocking
		// BDDMockito.given(webClient.method(HttpMethod.GET)
		// 		.uri("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + writerSignUpDto.getSummonerName())
		// 		.retrieve()
		// 		.bodyToMono(
		// 			new ParameterizedTypeReference<RiotApiSummonerInfoResponseDto>() {
		// 			}))
		// 	.willReturn(Mono.just(RiotApiSummonerInfoResponseDto.builder()
		// 		.id("id")
		// 		.accountId("accountId")
		// 		.puuid("puuid")
		// 		.name(writerSignUpDto.getSummonerName())
		// 		.revisionDate(100)
		// 		.profileIconId(100)
		// 		.summonerLevel(57)
		// 		.build()));

		// when
		MvcResult mvcResult = mock.perform(MockMvcRequestBuilders.post("/elections")
				.session(mockHttpSession)
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(electionAddDto)))
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

		mockServer.verify(
			HttpRequest.request()
				.withMethod(HttpMethod.GET.name())
				.withPath("/lol/summoner/v4/summoners/by-name/" + writerSignUpDto.getSummonerName()),
			VerificationTimes.exactly(1)
		);
	}

	public <T> String toJson(T data) throws JsonProcessingException {
		return objectMapper.writeValueAsString(data);
	}
}
