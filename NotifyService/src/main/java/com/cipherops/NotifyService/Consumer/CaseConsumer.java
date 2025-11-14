package com.cipherops.NotifyService.Consumer;


import com.cipherops.NotifyService.Service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CaseConsumer {

    @Autowired
    private NotificationService notificationService;

    // 👇 Add the @KafkaListener annotation on this method
    @KafkaListener(topics = "CASE_NOTE_ADDED", groupId = "notify-service")
    public void consumeCaseNoteAdded(String message) {
        log.info("📩 Received event on topic CASE_NOTE_ADDED: {}", message);
        notificationService.processEvent("case.note.added", message);
    }

    @KafkaListener(topics = "CASE_SEVERITY_CHANGED", groupId = "notify-service")
    public void consumeCaseSeverityChanged(String message) {
        log.info("📩 Received event on topic CASE_SEVERITY_CHANGED: {}", message);
        notificationService.processEvent("case.severity.changed", message);
    }
}

