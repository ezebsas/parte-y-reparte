package com.grupo2.parteyreparte.mappers;

import com.grupo2.parteyreparte.dtos.NotificationDTO;
import com.grupo2.parteyreparte.models.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public NotificationDTO mapToNotificationDTO(Notification notification) {
        return new NotificationDTO(notification.getTitle(), notification.getMessage(), notification.getDate().toString(), notification.getProduct().getId());
    }

}
