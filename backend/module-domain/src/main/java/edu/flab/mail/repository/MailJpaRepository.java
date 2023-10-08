package edu.flab.mail.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.flab.mail.domain.Mail;

public interface MailJpaRepository extends JpaRepository<Mail, Long> {
	Optional<Mail> findByAuthCode(String uuid);

	@Query("SELECT m from Mail m WHERE m.endedAt < :currentTime")
	List<Mail> findByEndedAt(OffsetDateTime currentTime);
}
