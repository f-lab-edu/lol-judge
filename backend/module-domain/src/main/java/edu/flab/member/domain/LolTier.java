package edu.flab.member.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@Embeddable
public sealed class LolTier permits LolTierUtil {

	@Getter
	public enum Color {
		NONE(1),
		IRON(2),
		BRONZE(3),
		SILVER(4),
		GOLD(5),
		PLATINUM(6),
		EMERALD(7),
		DIAMOND(8),
		MASTER(9),
		GRAND_MASTER(10),
		CHALLENGER(11);

		private final long score;

		Color(long score) {
			this.score = score;
		}
	}

	public enum Level {
		NONE, I, II, III, IV
	}

	@Enumerated(EnumType.STRING)
	private Color color;

	@Enumerated(EnumType.STRING)
	private Level level;

	private int point;
}
