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
		NONE(0),
		IRON(1),
		BRONZE(2),
		SILVER(3),
		GOLD(4),
		PLATINUM(5),
		EMERALD(6),
		DIAMOND(7),
		MASTER(8),
		GRAND_MASTER(9),
		CHALLENGER(10);

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
