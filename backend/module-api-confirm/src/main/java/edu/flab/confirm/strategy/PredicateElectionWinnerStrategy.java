package edu.flab.confirm.strategy;

import org.springframework.stereotype.Component;

import edu.flab.election.domain.Candidate;
import edu.flab.election.domain.Election;

@Component
public interface PredicateElectionWinnerStrategy {
	Candidate predicate(Election election);
}
