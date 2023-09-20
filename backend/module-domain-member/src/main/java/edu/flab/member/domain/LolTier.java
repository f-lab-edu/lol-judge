package edu.flab.member.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	private Color color;
	private int level;
	private int point;

	protected LolTier(Color color, Integer level, Integer point) {
		this.color = color;
		this.level = level;
		this.point = point;
	}
}
