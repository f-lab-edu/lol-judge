package edu.flab.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.flab.notification.domain.Notification;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {

}
