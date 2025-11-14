package com.cipherops.NotifyService.Controller;

//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.cipherops.NotifyService.Model.Notification;
import com.cipherops.NotifyService.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/me/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository repo;

    // ✅ Get all notifications for user
    @GetMapping
    public List<Notification> getMyNotifications(@RequestParam String userId) {
        return repo.findByAssigneeIdOrderByCreatedAtDesc(userId);
    }

    // TODO: request param to request header value X-USER-ID
    // ✅ Mark one or more as read
    @PostMapping("/mark-read")
    public void markRead(@RequestParam String userId, @RequestBody List<String> ids) {
        ids.forEach(id -> repo.findById(id).ifPresent(n -> {
            if (n.getAssigneeId().equals(userId)) {
                n.setStatus("READ");
                repo.save(n);
            }
        }));
    }

    // ✅ Mark all unread as read
    @PostMapping("/mark-all-read")
    public void markAllRead(@RequestParam String userId) {
        repo.findByAssigneeIdOrderByCreatedAtDesc(userId).stream()
                .filter(n -> "UNREAD".equals(n.getStatus()))
                .forEach(n -> {
                    n.setStatus("READ");
                    repo.save(n);
                });
    }
}
