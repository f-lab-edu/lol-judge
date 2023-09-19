package edu.flab.election.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ElectionInfoFindResponseDto {
	private Long lastId;
	private List<ElectionInfoDto> electionInfoDtoList;
}
