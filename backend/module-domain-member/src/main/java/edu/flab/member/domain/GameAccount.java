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
import lombok.Builder.Default;
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
	private String nickname;

	@NotBlank
	@Length(max = 24)
	private String lolId;

	@Default
	@Enumerated(EnumType.STRING)
	private GamePosition position = GamePosition.NONE;

	@Default
	@Embedded
	private LolTier lolTier = LolTierUtil.createUnRankTier();

	//== 연관관계 매핑 ==//
	public void setMember(Member member) {
		this.member = member;
	}
}
