package com.ets.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ets.model.Notiifications;
import com.ets.repository.NotiificationsRepository;

@Service
public class NotiificationsService {

    @Autowired
    private NotiificationsRepository notiificationsRepository;

    public List<Notiifications> getAllNotifications() {
        return notiificationsRepository.findAll();
    }

    public Optional<Notiifications> getNotificationById(Long id) {
        return notiificationsRepository.findById(id);
    }

    public List<Notiifications> getUnreadNotifications() {
        return notiificationsRepository.findByIsRead(false);
    }

    public Notiifications createNotification(Notiifications notiification) {
        return notiificationsRepository.save(notiification);
    }

    public Notiifications updateNotification(Long id, Notiifications updatedNotification) {
        return notiificationsRepository.findById(id).map(notification -> {
            notification.setTitle(updatedNotification.getTitle());
            notification.setMessage(updatedNotification.getMessage());
            notification.setType(updatedNotification.getType());
            notification.setRead(updatedNotification.isRead());
            return notiificationsRepository.save(notification);
        }).orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
    }

    public Notiifications markAsRead(Long id) {
        return notiificationsRepository.findById(id).map(notification -> {
            notification.setRead(true);
            return notiificationsRepository.save(notification);
        }).orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
    }

    public void deleteNotification(Long id) {
        if (!notiificationsRepository.existsById(id)) {
            throw new RuntimeException("Notification not found with id: " + id);
        }
        notiificationsRepository.deleteById(id);
    }
}
