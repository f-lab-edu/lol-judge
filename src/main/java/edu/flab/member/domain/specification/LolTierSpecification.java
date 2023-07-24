package edu.flab.member.domain.specification;

import java.util.Set;

import edu.flab.member.domain.LolTier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LolTierSpecification {

	private final Set<LolTier.Color> lolTierColors;
	private final int lolTierMinLevel;
	private final int lolTierMaxLevel;
	private final int lolTierMinPoint;
	private final int lolTierMaxPoint;

	public boolean isSatisfied(LolTier candidate) {
		return isValidColor(candidate.getColor()) && isValidLevel(candidate.getLevel()) && isValidPoint(
			candidate.getPoint());
	}

	private boolean isValidColor(LolTier.Color color) {
		return lolTierColors.contains(color);
	}

	private boolean isValidLevel(int level) {
		return lolTierMinLevel <= level && level <= lolTierMaxLevel;
	}

	private boolean isValidPoint(int point) {
		return lolTierMinPoint <= point && point <= lolTierMaxPoint;
	}
}
