package edu.flab.member.domain;

public record RankScore(long score) {

	/**
	 * 회원의 랭킹은 rankScore 가 높은 순서로 정해진다.
	 * rankScore 는 [judgePoint, lolTier, id] 정보를 기반으로 계산된다.
	 * - 16자리 숫자로 구성
	 * - 큰 자릿수부터 순서대로 8자리, 2자리, 2자리, 4자리는 각각 judgePoint, LolTier.color, LolTier.level, LolTier.point 의 값으로 채워진다.
	 * 회원의 judgePoint, lolTier 정보가 변경될 때마다, 이 함수를 호출하여 rankScore 를 갱신한다.
	 * ====
	 * 고민. RankScore 를 계산하기 더 좋은 방법은 없을까?
	 */
	public static RankScore calc(Member member) {
		LolTier lolTier = member.getGameAccount().getLolTier();

		String judgePoint = String.format("%08d", member.getJudgePoint());
		String lolTierColor = String.format("%02d", lolTier.getColor().ordinal());
		String lolTierLevel = String.format("%02d", lolTier.getLevel());
		String lolTierPoint = String.format("%04d", lolTier.getPoint());

		long totalScore = Long.parseLong(judgePoint + lolTierColor + lolTierLevel + lolTierPoint);

		return new RankScore(totalScore);
	}
}
