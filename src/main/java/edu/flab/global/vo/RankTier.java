package edu.flab.global.vo;

/**
 * 외부 정보를 내부 코드에서 관리할 때 변경 가능성을 고려하는 방법에 대한 고민
 * [선택1] enum을 사용하면 입력에 제한을 둘 수 있다.
 * 반면에 티어그룹이 하나가 추가되는 경우, 별도의 작업을 해줘야한다.
 * 예를 들어, 에메랄드 티어 새로 생기는 경우, 에메랄드1-에메랄드4 티어를 추가해야한다.
 * [선택2] class를 사용하여 확장 가능성을 열어둔다.
 * 클래스에 group, level 필드를 선언하고, 클라이언트코드에 입력을 맡긴다.
 * 이 경우, 입력의 제한을 두기 위해 생성자에 유효성 검사 로직을 구현해야 한다.
 *
 */
public enum RankTier {

	IRON1("IRON", 1),
	IRON2("IRON", 2),
	IRON3("IRON", 3),
	IRON4("IRON", 4),
	BRONZE1("BRONZE", 1),
	BRONZE2("BRONZE", 2),
	BRONZE3("BRONZE", 3),
	BRONZE4("BRONZE", 4),
	SILVER1("SILVER", 1),
	SILVER2("SILVER", 2),
	SILVER3("SILVER", 3),
	SILVER4("SILVER", 4),
	GOLD1("GOLD", 1),
	GOLD2("GOLD", 2),
	GOLD3("GOLD", 3),
	GOLD4("GOLD", 4),
	PLATINUM1("PLATINUM", 1),
	PLATINUM2("PLATINUM", 2),
	PLATINUM3("PLATINUM", 3),
	PLATINUM4("PLATINUM", 4),
	DIAMOND1("DIAMOND", 1),
	DIAMOND2("DIAMOND", 2),
	DIAMOND3("DIAMOND", 3),
	DIAMOND4("DIAMOND", 4),
	MASTER("MASTER", null),
	GRAND_MASTER("GRAND_MASTER", null),
	CHALLENGER("CHALLENGER", null);

	final String group;
	final Integer level;

	RankTier(String group, Integer level) {
		this.group = group;
		this.level = level;
	}
}
