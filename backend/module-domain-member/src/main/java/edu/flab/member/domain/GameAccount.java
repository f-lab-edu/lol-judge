package edu.flab.member.domain;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@EqualsAndHashCode(exclude = "member")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameAccount {
	@Id
	@Column(name = "game_account_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(mappedBy = "gameAccount", cascade = CascadeType.ALL)
	private Member member;

	@NotBlank
	@Length(max = 16)
	private String nickname;    // 리그오브레전드 닉네임

	@NotBlank
	@Length(max = 24)
	private String lolId;  // 리그오브레전드 계정 아이디

	@Enumerated(EnumType.STRING)
	private GamePosition position;	// 리그오브레전드 포지션

	@Embedded
	private LolTier lolTier;    // 리그오브레전드 랭크 티어 정보

	//== 연관관계 매핑 ==//
	public void setMember(Member member) {
		this.member = member;
	}
}
