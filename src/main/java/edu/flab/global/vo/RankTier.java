package edu.flab.global.vo;

public enum RankTier {

	IRON1("IRON", 1), IRON2("IRON", 2), IRON3("IRON", 3), IRON4("IRON", 4), BRONZE1("BRONZE", 1), BRONZE2("BRONZE",
		2), BRONZE3("BRONZE", 3), BRONZE4("BRONZE", 4), SILVER1("SILVER", 1), SILVER2("SILVER", 2), SILVER3("SILVER",
		3), SILVER4("SILVER", 4), GOLD1("GOLD", 1), GOLD2("GOLD", 2), GOLD3("GOLD", 3), GOLD4("GOLD", 4), PLATINUM1(
		"PLATINUM", 1), PLATINUM2("PLATINUM", 2), PLATINUM3("PLATINUM", 3), PLATINUM4("PLATINUM", 4), DIAMOND1(
		"DIAMOND", 1), DIAMOND2("DIAMOND", 2), DIAMOND3("DIAMOND", 3), DIAMOND4("DIAMOND", 4), MASTER(
		"MASTER"), GRAND_MASTER("GRAND_MASTER"), CHALLENGER("CHALLENGER");

	final String group;
	Integer level;

	RankTier(String group) {
		this.group = group;
	}

	RankTier(String group, int level) {
		this.group = group;
		this.level = level;
	}
}
