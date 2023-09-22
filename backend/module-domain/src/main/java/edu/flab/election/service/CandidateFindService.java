package edu.flab.election.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import edu.flab.election.domain.Candidate;
import edu.flab.election.repository.CandidateJpaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandidateFindService {

	private final CandidateJpaRepository candidateJpaRepository;

	public Candidate findById(Long candidateId) {
		return candidateJpaRepository.findById(candidateId)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 후보자입니다 <candidateId = " + candidateId + ">"));
	}
}
