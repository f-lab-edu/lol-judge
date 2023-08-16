package edu.flab.notify.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApiUrlUtilTest {

	@Test
	@DisplayName("재판 객체가 주어졌을 때, 해당 재판의 수정 API URL 을 반환한다")
	void test1() {
		// given
		ApiUrlProperties apiUrlProperties = new ApiUrlProperties();
		apiUrlProperties.setHost("localhost");
		apiUrlProperties.setPort("8080");
		ApiUrlUtil apiUrlUtil = new ApiUrlUtil(apiUrlProperties);

		// when
		String electionApiUrl = apiUrlUtil.getElectionApiUrl(1L);

		// then
		assertThat(electionApiUrl).isEqualTo("https://localhost:8080/api/election/1");
	}
}