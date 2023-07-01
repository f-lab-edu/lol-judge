package edu.flab.member.dto;

import edu.flab.global.vo.RankTier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignUpDto {
	private String email;
	private String password;
	private String profileUrl;
	private String nickname;
	private String gameLoginId;
	private RankTier rankTier;
}
