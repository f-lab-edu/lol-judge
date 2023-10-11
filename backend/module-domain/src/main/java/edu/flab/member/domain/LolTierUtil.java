package edu.flab.member.domain;

import static edu.flab.member.domain.LolTier.Color.*;

import java.util.Set;

import edu.flab.member.domain.specification.LolTierSpecification;

public non-sealed class LolTierUtil extends LolTier {

	private static final Set<LolTier.Color> NORMAL_TIER_COLORS = Set.of(IRON, BRONZE, SILVER, GOLD, PLATINUM, DIAMOND);

	private static final Set<LolTier.Color> HIGH_TIER_COLORS = Set.of(MASTER, GRAND_MASTER, CHALLENGER);

	private static final LolTierSpecification NORMAL_TIER_SPEC = new LolTierSpecification(NORMAL_TIER_COLORS, Level.IV,
		Level.I, 0, 100);

	private static final LolTierSpecification HIGH_TIER_SPEC = new LolTierSpecification(HIGH_TIER_COLORS, Level.I,
		Level.I, 0, 10000);

	public static LolTier createUnRankTier() {
		return new LolTier(NONE, Level.NONE, 0);
	}

	public static LolTier createTier(Color color, Level level, int point) {
		LolTier lolTier = new LolTier(color, level, point);

		if (HIGH_TIER_SPEC.isSatisfied(lolTier) || NORMAL_TIER_SPEC.isSatisfied(lolTier)) {
			return lolTier;
		}
		throw new IllegalArgumentException(
			String.format("LolTier 인스턴스 생성 과정에서 문제가 발생하였습니다. <color:%s> <level:%s> <point:%d>", color, level, point));
	}

	public static LolTier createTier(String color, String level, int point) {
		return createTier(Color.valueOf(color), Level.valueOf(level), point);
	}
}
