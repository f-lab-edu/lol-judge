package edu.flab.mail.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.flab.mail.domain.Mail;

public interface MailJpaRepository extends JpaRepository<Mail, Long> {
	Optional<Mail> findByUuid(String uuid);
}
