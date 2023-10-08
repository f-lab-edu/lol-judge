package edu.flab.mail.domain;

import java.time.OffsetDateTime;

import edu.flab.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String authCode;

	@NotNull
	private OffsetDateTime endedAt;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	public Mail(String authCode, OffsetDateTime endedAt) {
		this.authCode = authCode;
		this.endedAt = endedAt;
	}

	//== 연관관계 편의 메서드 ==//

	public void setMember(Member member) {
		this.member = member;
	}
}
