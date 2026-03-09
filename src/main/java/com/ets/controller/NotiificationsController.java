package com.ets.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ets.model.Notiifications;
import com.ets.service.NotiificationsService;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotiificationsController {

    @Autowired
    private NotiificationsService notiificationsService;

    @GetMapping
    public List<Notiifications> getAllNotifications() {
        return notiificationsService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public Notiifications getNotificationById(@PathVariable Long id) {
        return notiificationsService.getNotificationById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
    }

    @GetMapping("/unread")
    public List<Notiifications> getUnreadNotifications() {
        return notiificationsService.getUnreadNotifications();
    }

    @PostMapping
    public Notiifications createNotification(@RequestBody Notiifications notiification) {
        return notiificationsService.createNotification(notiification);
    }

    @PutMapping("/{id}")
    public Notiifications updateNotification(@PathVariable Long id, @RequestBody Notiifications notiification) {
        return notiificationsService.updateNotification(id, notiification);
    }

    @PutMapping("/{id}/read")
    public Notiifications markAsRead(@PathVariable Long id) {
        return notiificationsService.markAsRead(id);
    }

    @DeleteMapping("/{id}")
    public String deleteNotification(@PathVariable Long id) {
        notiificationsService.deleteNotification(id);
        return "Notification deleted successfully";
    }
}