package edu.flab.election.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.flab.election.domain.Election;
import edu.flab.election.domain.ElectionStatus;
import edu.flab.election.dto.ElectionInfoDto;
import edu.flab.election.dto.ElectionInfoFindResponseDto;
import edu.flab.member.TestFixture;

@ExtendWith(MockitoExtension.class)
class ElectionInfoFindServiceTest {

	@InjectMocks
	private ElectionInfoFindService sut;

	@Mock
	private ElectionFindService electionFindService;

	@Test
	@DisplayName("생성 날짜를 기준으로 최신 재판의 요약 정보를 pageSize 개수 만큼 조회할 수 있다")
	void test1() {
		// given
		Election election0 = TestFixture.getElection();
		Election election1 = TestFixture.getElection();

		when(electionFindService.findAllByStatus(anyLong(), any())).thenReturn(
			List.of(election1, election0));

		// when
		ElectionInfoFindResponseDto electionInfo = sut.findLatestElections(5L, ElectionStatus.IN_PROGRESS);

		// then
		SoftAssertions.assertSoftly(softly -> {
			softly.assertThat(electionInfo.getLastId()).isEqualTo(election0.getId());
			softly.assertThat(electionInfo.getElectionInfoDtoList())
				.isEqualTo(List.of(
					new ElectionInfoDto(election1),
					new ElectionInfoDto(election0)
				));
		});
	}

	@Test
	@DisplayName("lastId 보다 작은 ID 값을 가진 재판 요약 정보를 pageSize 개수 만큼 조회할 수 있다")
	void test2() {
		// given
		Election election0 = TestFixture.getElection();
		Election election1 = TestFixture.getElection();
		Election election2 = TestFixture.getElection();

		when(electionFindService.findAllByStatus(anyLong(), anyLong(), any())).thenReturn(
			List.of(election2, election1, election0));

		// when
		// ID 값이 3L 미만인 재판 3개 조회한다.
		ElectionInfoFindResponseDto electionInfo = sut.findAllWithPagination(3L, 3L, ElectionStatus.IN_PROGRESS);

		// then
		SoftAssertions.assertSoftly(softly -> {
			softly.assertThat(electionInfo.getLastId()).isEqualTo(election0.getId());
			softly.assertThat(electionInfo.getElectionInfoDtoList())
				.isEqualTo(List.of(
					new ElectionInfoDto(election2),
					new ElectionInfoDto(election1),
					new ElectionInfoDto(election0)
				));
		});
	}
}
