package edu.flab.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.flab.global.vo.RankTier;
import edu.flab.member.dto.MemberSignUpDto;

@Tag("integration")
@SpringBootTest
class MemberControllerTest {

	@Autowired
	private MemberController memberController;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mock;

	@BeforeEach
	void setUp() {
		mock = MockMvcBuilders.standaloneSetup(memberController).build();
	}

	@Test
	void 회원가입() throws Exception {
		MemberSignUpDto signUpDto = MemberSignUpDto.builder()
			.email("admin@example.com")
			.password("aB#12345")
			.profileUrl("https://cloud.example.com/bucket/profile_image.jpg")
			.gameLoginId("lolId1234")
			.nickname("hide on bush")
			.rankTier(RankTier.CHALLENGER)
			.build();

		mock.perform(post("/signUp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(signUpDto)))
			.andExpect(status().isOk())
			.andDo(print());
	}

	public <T> String toJson(T data) throws JsonProcessingException {
		return objectMapper.writeValueAsString(data);
	}
}
