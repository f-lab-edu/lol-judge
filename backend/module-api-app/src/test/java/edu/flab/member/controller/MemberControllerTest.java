package edu.flab.member.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.flab.member.TestFixture;
import edu.flab.member.domain.Member;
import edu.flab.member.dto.MemberLoginRequestDto;
import edu.flab.member.dto.MemberSignUpDto;
import edu.flab.web.handler.GlobalExceptionHandler;

@Tag("integration")
@Transactional
@Testcontainers
@SpringBootTest
class MemberControllerTest {

	@Autowired
	private MemberController memberController;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mock;

	@Container
	private static GenericContainer redis = new GenericContainer(
		DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);

	@Container
	private static RabbitMQContainer rabbitmq = new RabbitMQContainer("rabbitmq:latest").withExposedPorts(5672, 15672);

	@BeforeEach
	void setUp() {
		redis.start();
		rabbitmq.start();
		mock = MockMvcBuilders.standaloneSetup(memberController)
			.setControllerAdvice(new GlobalExceptionHandler())
			.build();
	}

	@DynamicPropertySource
	static void registerProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.rabbitmq.host", rabbitmq::getHost);
		registry.add("spring.rabbitmq.port", rabbitmq::getAmqpPort);
		registry.add("spring.rabbitmq.username", rabbitmq::getAdminUsername);
		registry.add("spring.rabbitmq.password", rabbitmq::getAdminPassword);
	}

	/**
	 * test fixture
	 */
	private final Member member = TestFixture.getMember();

	private final MemberSignUpDto signUpDto = MemberSignUpDto.builder()
		.email(member.getEmail())
		.password(member.getPassword())
		.lolId(member.getGameAccount().getLolId())
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
				.content(toJson(signUpDto)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("비밀번호 규칙을 어겨서 회원가입에 실패한다")
	void test2() throws Exception {
		MemberSignUpDto passwordRuleViolation = signUpDto;
		passwordRuleViolation.setPassword("pwd1234");

		mock.perform(MockMvcRequestBuilders.post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(passwordRuleViolation)))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("회원가입 이후 로그인에 성공한다")
	void test3() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(signUpDto)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());

		mock.perform(MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(loginDto)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("회원가입 이후 비밀번호를 틀려 로그인에 실패한다")
	void test4() throws Exception {
		MemberLoginRequestDto wrongPasswordDto = loginDto;
		wrongPasswordDto.setPassword("WrongPassword");

		mock.perform(MockMvcRequestBuilders.post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(signUpDto)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());

		mock.perform(MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(wrongPasswordDto)))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("존재하지않는 이메일를 입력하여 로그인에 실패한다")
	void test5() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(loginDto)))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andDo(MockMvcResultHandlers.print());
	}

	public <T> String toJson(T data) throws JsonProcessingException {
		return objectMapper.writeValueAsString(data);
	}
}
