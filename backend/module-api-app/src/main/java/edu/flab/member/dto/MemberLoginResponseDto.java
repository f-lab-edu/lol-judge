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
	private String lolId;
	private String email;

	public MemberLoginResponseDto(Member member) {
		this.memberId = member.getId();
		this.lolId = member.getGameAccount().getEncryptedId();
		this.email = member.getEmail();
	}
}
