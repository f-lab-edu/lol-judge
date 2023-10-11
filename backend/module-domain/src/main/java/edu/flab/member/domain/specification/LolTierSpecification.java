package edu.flab.member.domain.specification;

import java.util.Set;

import edu.flab.member.domain.LolTier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LolTierSpecification {

	private final Set<LolTier.Color> lolTierColors;
	private final LolTier.Level lolTierMinLevel;
	private final LolTier.Level lolTierMaxLevel;
	private final int lolTierMinPoint;
	private final int lolTierMaxPoint;

	public boolean isSatisfied(LolTier lolTier) {
		return isValidColor(lolTier.getColor()) && isValidLevel(lolTier.getLevel()) && isValidPoint(
			lolTier.getPoint());
	}

	private boolean isValidColor(LolTier.Color color) {
		return lolTierColors.contains(color);
	}

	private boolean isValidLevel(LolTier.Level level) {
		return level.compareTo(lolTierMinLevel) >= 0 && level.compareTo(lolTierMaxLevel) <= 0;
	}

	private boolean isValidPoint(int point) {
		return lolTierMinPoint <= point && point <= lolTierMaxPoint;
	}
}
