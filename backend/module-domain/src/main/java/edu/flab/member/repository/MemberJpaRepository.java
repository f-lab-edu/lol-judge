package edu.flab.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import edu.flab.member.domain.Member;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {
	@NonNull
	@Override
	<S extends Member> S save(@NonNull S entity);

	boolean existsByEmail(String email);

	Optional<Member> findByIdAndDeletedAndAuthenticated(Long id, boolean active, boolean authenticated);

	Optional<Member> findByEmailAndDeletedAndAuthenticated(String email, boolean active, boolean authenticated);
}
