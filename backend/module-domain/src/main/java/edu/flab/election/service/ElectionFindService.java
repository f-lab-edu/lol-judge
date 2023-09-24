package edu.flab.election.service;

import static edu.flab.election.domain.QCandidate.*;
import static edu.flab.election.domain.QElection.*;
import static edu.flab.election.domain.QVote.*;
import static edu.flab.member.domain.QGameAccount.*;
import static edu.flab.member.domain.QMember.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import edu.flab.election.domain.Election;
import edu.flab.election.domain.ElectionStatus;
import edu.flab.election.repository.ElectionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectionFindService {
	private static final String NOT_FOUND_EXCEPTION_MESSAGE_FORMAT = "재판을 찾을 수 없습니다 < id = %d >";

	private final ElectionJpaRepository electionJpaRepository;
	private final JPAQueryFactory jpaQueryFactory;

	public Election findElection(Long electionId) {
		return electionJpaRepository.findById(electionId).orElseThrow(
			() -> new NoSuchElementException(String.format(NOT_FOUND_EXCEPTION_MESSAGE_FORMAT, electionId)));
	}

	public List<Election> findAllByStatus(ElectionStatus status) {
		return electionJpaRepository.findAllByStatus(status);
	}

	public List<Election> findAllByStatus(long pageSize, ElectionStatus status) {
		return jpaQueryFactory.selectFrom(election)
			.innerJoin(election.member, member)
			.innerJoin(member.gameAccount, gameAccount)
			.leftJoin(election.candidates, candidate)
			.leftJoin(candidate.votes, vote)
			.leftJoin(vote.member, member)
			.where(eqStatus(status))
			.orderBy(election.id.desc())
			.limit(pageSize)
			.fetch();
	}

	public List<Election> findAllByStatus(long lastId, long pageSize, ElectionStatus status) {
		return jpaQueryFactory.selectFrom(election)
			.innerJoin(election.member, member)
			.innerJoin(member.gameAccount, gameAccount)
			.leftJoin(election.candidates, candidate)
			.leftJoin(candidate.votes, vote)
			.leftJoin(vote.member, member)
			.where(eqStatus(status), ltElectionId(lastId))
			.orderBy(election.id.desc())
			.limit(pageSize)
			.fetch();
	}

	public List<Election> findAllByStatusOrderByTotalVotedCount(long totalVotedCount, long pageSize,
		ElectionStatus status) {
		return jpaQueryFactory.selectFrom(election)
			.innerJoin(election.member, member)
			.innerJoin(member.gameAccount, gameAccount)
			.leftJoin(election.candidates, candidate)
			.leftJoin(candidate.votes, vote)
			.leftJoin(vote.member, member)
			.where(eqStatus(status), ltVotedCount(totalVotedCount))
			.orderBy(election.totalVotedCount.desc())
			.limit(pageSize)
			.fetch();
	}

	private BooleanBuilder ltVotedCount(Long totalVotedCount) {
		return nullSafeBuilder(() -> election.totalVotedCount.lt(totalVotedCount));
	}

	private BooleanBuilder ltElectionId(Long lastId) {
		return nullSafeBuilder(() -> election.id.lt(lastId));
	}

	private BooleanBuilder eqStatus(ElectionStatus status) {
		return nullSafeBuilder(() -> election.status.eq(status));
	}

	private static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
		try {
			return new BooleanBuilder(f.get());
		} catch (IllegalArgumentException | NullPointerException e) {
			return new BooleanBuilder();
		}
	}
}
