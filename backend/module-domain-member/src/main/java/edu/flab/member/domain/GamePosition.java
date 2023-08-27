package edu.flab.member.domain;

public enum GamePosition {
	NONE, TOP, JUNGLE, MID, ADC, SUPPORT;

	public static GamePosition of(String name) {
		if (name == null) {
			return NONE;
		}
		return GamePosition.valueOf(name);
	}
}
