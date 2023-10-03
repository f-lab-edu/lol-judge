package edu.flab.member.dto;

import edu.flab.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLoginResponseDto {
	private Long memberId;
	private String summonerName;
	private String email;

	public MemberLoginResponseDto(Member member) {
		this.memberId = member.getId();
		this.summonerName = member.getGameAccount().getSummonerName();
		this.email = member.getEmail();
	}
}
