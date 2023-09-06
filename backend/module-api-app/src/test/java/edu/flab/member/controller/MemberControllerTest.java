package edu.flab.member.controller;

import org.junit.jupiter.api.BeforeEach;
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
	private static GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);

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

	@Test
	void 회원가입에_성공한다() throws Exception {
		MemberSignUpDto signUpDto = MemberSignUpDto.builder()
			.email("admin@example.com")
			.password("aB#12345")
			.gameLoginId("lolId1234")
			.position("MID")
			.build();

		mock.perform(MockMvcRequestBuilders.post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(signUpDto)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void 비밀번호규칙을_어겨서_회원가입에_실패한다() throws Exception {
		MemberSignUpDto signUpDto = MemberSignUpDto.builder()
			.email("admin@example.com")
			.password("aB#")
			.gameLoginId("lolId1234")
			.build();

		mock.perform(MockMvcRequestBuilders.post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(signUpDto)))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void 회원가입_이후_로그인에_성공한다() throws Exception {
		MemberSignUpDto signUpDto = MemberSignUpDto.builder()
			.email("admin@example.com")
			.password("aB#12345")
			.gameLoginId("lolId1234")
			.position("MID")
			.build();

		MemberLoginRequestDto loginDto = MemberLoginRequestDto.builder()
			.email(signUpDto.getEmail())
			.password(signUpDto.getPassword())
			.build();

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
	void 회원가입_이후_비밀번호를_틀려_로그인에_실패한다() throws Exception {
		MemberSignUpDto signUpDto = MemberSignUpDto.builder()
			.email("admin@example.com")
			.password("aB#12345")
			.gameLoginId("lolId1234")
			.position("MID")
			.build();

		MemberLoginRequestDto loginDto = MemberLoginRequestDto.builder()
			.email(signUpDto.getEmail())
			.password("invalid password")
			.build();

		mock.perform(MockMvcRequestBuilders.post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(signUpDto)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());

		mock.perform(MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(loginDto)))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void 존재하지않는_이메일를_입력하여_로그인에_실패한다() throws Exception {
		MemberLoginRequestDto loginDto = MemberLoginRequestDto.builder()
			.email("admin@example.com")
			.password("aB#12345")
			.build();

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
