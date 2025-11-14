package com.cipherops.NotifyService.Service;

import com.cipherops.NotifyService.Controller.NotificationSseController;
import com.cipherops.NotifyService.Model.Notification;
import com.cipherops.NotifyService.Model.universalCase.UniversalCase;
import com.cipherops.NotifyService.Repository.NotificationRepository;
import com.cipherops.NotifyService.Repository.UniversalCaseRespository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UniversalCaseRespository universalCaseRespository;

    @Autowired
    private NotificationSseController sseController;


    public void processEvent(String topic, String payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode json = objectMapper.readTree(payload);

            System.out.println(payload);
            System.out.println(topic);

            List<String> recipients = resolveRecipients(topic, json);

            System.out.println(recipients);

            for (String userId : recipients) {

                Notification n = Notification.builder()
                        .assigneeId(userId)                   // assignee id
                        .title(resolveTitle(topic))           //
                        .message(resolveMessage(topic, json))        //
                        .eventType(topic)
                        .status("UNREAD")       //
                        .createdAt(Instant.now())   //
                        .build();

                notificationRepository.save(n);

                sseController.sendToUser(userId, n);
                log.info("✅ Notification sent to {}", userId);
            }

        } catch (Exception e) {
            log.error("❌ Failed to process event", e);
        }
    }

    private List<String> resolveRecipients(String topic, JsonNode json) {

        if (topic.equals("case.severity.changed") || topic.equals("case.note.added")) {

            String caseId = json.path("caseId").asText(null);

            if (caseId == null) return List.of(); // fallback if bad payload

            // Fetch universal case
            UniversalCase caseObj = universalCaseRespository.findByCaseId(caseId)
                    .orElse(null);

            if (caseObj == null) {
                log.warn("No UniversalCase found for caseId={}", caseId);
                return List.of();
            }

            // Extract assignee id (this is the user who should get the notification)
            UniversalCase.Assignee assignee = caseObj.getAssignee();

            if (assignee == null || assignee.getId() == null) {
                log.warn("No assignee found for caseId={}", caseId);
                return List.of();
            }

            return List.of(assignee.getId());  // <- Send to actual assigned user
        }

        return List.of();
    }


    private String resolveTitle(String topic) {
        return switch (topic) {
            case "case.note.added" -> "New Note Added to Case";
            case "case.severity.changed" -> "Case Severity Updated";
            default -> "System Notification";
        };
    }

    private String resolveMessage(String topic, JsonNode json) {
        return switch (topic) {
            case "case.note.added" ->
                    "A new note has been added by " + json.path("author").asText("Unknown Author")
                            + " for case ID: " + json.path("caseId").asText();

            case "case.severity.changed" ->
                    "Severity changed for case: " + json.path("caseId").asText("Unknown Case")
                            + " (Tenant: " + json.path("tenantId").asText("N/A")
                            + " (Project: " + json.path("projectId").asText("N/A") + ")";

            default ->
                    "Event received: " + json.path("eventType").asText("Unknown Event");
        };
    }
}

