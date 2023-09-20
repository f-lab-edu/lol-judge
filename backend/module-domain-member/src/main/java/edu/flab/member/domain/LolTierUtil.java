package edu.flab.member.domain;

import static edu.flab.member.domain.LolTier.Color.*;

import java.util.Set;

import edu.flab.member.domain.specification.LolTierSpecification;

public non-sealed class LolTierUtil extends LolTier {

	public static LolTier createUnRankTier() {
		return new LolTier(NONE, 0, 0);
	}

	public static LolTier createNormalTier(LolTier.Color color, int level, int point) {
		Set<LolTier.Color> normalTierColors = Set.of(IRON, BRONZE, SILVER, GOLD, PLATINUM, DIAMOND);
		LolTierSpecification normalTierSpec = new LolTierSpecification(normalTierColors, 1, 4, 0, 100);
		return getInstance(normalTierSpec, color, level, point);
	}

	public static LolTier createHighTier(LolTier.Color color, int point) {
		Set<LolTier.Color> highTierColors = Set.of(MASTER, GRAND_MASTER, CHALLENGER);
		LolTierSpecification highTierSpec = new LolTierSpecification(highTierColors, 0, 0, 0, Integer.MAX_VALUE);
		return getInstance(highTierSpec, color, 0, point);
	}

	public static LolTier getInstance(LolTierSpecification spec, LolTier.Color color, int level, int point) {
		LolTier instance = new LolTier(color, level, point);
		if (spec.isSatisfied(instance)) {
			return instance;
		}
		throw new IllegalArgumentException(
			String.format("LolTier 인스턴스 생성 과정에서 문제가 발생하였습니다. <color:%s> <level:%d> <point:%d>", color, level, point));
	}
}
