package edu.flab.member.dto;

import edu.flab.member.domain.JudgePointHistoryType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJudgePointHistoryDto {
	private long amount;
	private JudgePointHistoryType judgePointHistoryType;
}
