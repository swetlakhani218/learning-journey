package com.cipherops.NotifyService.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Document(collection = "notifications")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    private String id;
    private String assigneeId;
    private String title;
    private String message;
    private String eventType; // e.g. case.note.added, case.severity.changed
    private String status; // UNREAD / READ
    private Instant createdAt;
}

