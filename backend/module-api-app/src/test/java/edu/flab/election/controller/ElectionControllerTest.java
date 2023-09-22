package edu.flab.election.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

import edu.flab.election.dto.ElectionRegisterRequestDto;
import edu.flab.election.dto.ElectionRegisterResponseDto;
import edu.flab.election.service.ElectionFindService;
import edu.flab.member.controller.MemberController;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.member.service.MemberSignUpService;
import edu.flab.web.config.LoginConstant;
import edu.flab.web.handler.GlobalExceptionHandler;
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

	@Container
	private static final GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);

	@Container
	private static final RabbitMQContainer rabbitmq = new RabbitMQContainer("rabbitmq:latest").withExposedPorts(5672, 15672);

	@BeforeEach
	void setUp() {
		redis.start();
		rabbitmq.start();
		mock = MockMvcBuilders.standaloneSetup(memberController, electionController)
			.setControllerAdvice(new GlobalExceptionHandler())
			.setCustomArgumentResolvers(new LoginArgumentResolver())
			.build();
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
		MemberSignUpDto hostSignUpDto = MemberSignUpDto.builder()
			.email("host@example.com")
			.password("aB#12345")
			.lolId("lolId1111")
			.build();

		MemberSignUpDto participantSignUpDto = MemberSignUpDto.builder()
			.email("participant@example.com")
			.password("aB#12345")
			.lolId("lolId2222")
			.build();

		Member hostMember = memberSignUpService.signUp(hostSignUpDto);
		Member participantMember = memberSignUpService.signUp(participantSignUpDto);

		ElectionRegisterRequestDto electionAddDto = ElectionRegisterRequestDto.builder()
			.opinion("원딜이 쌍둥이 포탑을 계속 쳤으면 게임을 끝낼 수 있었다 vs 카밀의 압박 때문에 할 수 없다")
			.youtubeUrl("https://youtube.com/7I5SKTY-JXc")
			.participantEmail(participantMember.getEmail())
			.cost(1000)
			.progressTime(60)
			.champion("shivana")
			.build();

		MockHttpSession mockHttpSession = new MockHttpSession();
		mockHttpSession.setAttribute(LoginConstant.LOGIN_SESSION_ATTRIBUTE, hostMember);

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
	}

	public <T> String toJson(T data) throws JsonProcessingException {
		return objectMapper.writeValueAsString(data);
	}
}
