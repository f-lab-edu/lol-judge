package edu.flab.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import edu.flab.member.domain.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
	@NonNull
	@Override
	<S extends Member> S save(@NonNull S entity);

	boolean existsByEmail(String email);

	Optional<Member> findByIdAndActive(Long id, boolean active);

	Optional<Member> findByEmailAndActive(String email, boolean active);
}
