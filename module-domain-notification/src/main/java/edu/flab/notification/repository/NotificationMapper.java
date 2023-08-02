package edu.flab.notification.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.flab.notification.domain.Notification;
import edu.flab.notification.dto.NotificationFindRequestDto;

@Mapper
public interface NotificationMapper {
	void save(Notification notification);

	void delete(Long id);

	List<Notification> findAll(NotificationFindRequestDto dto);

	List<Notification> findNotReadNotifications(NotificationFindRequestDto dto);
}
