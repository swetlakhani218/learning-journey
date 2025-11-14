


package com.cipherops.OutboundRelayService.Service;

import com.cipherops.OutboundRelayService.Repository.UniversalCaseNoteRepository;
import com.cipherops.OutboundRelayService.Model.universalCase.UniversalCaseNote;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OutboundRelayService {

    @Autowired
    private UniversalCaseNoteRepository noteRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    public void publishPendingEvents() {
        processPendingNotes();
    }

    private void processPendingNotes() {
        List<UniversalCaseNote> pendingNotes = noteRepository
                .findTop100ByOutboxStatusOrderByOutboxCreatedAtAsc("PENDING");
        log.info("📥 Found {} pending notes from MongoDB", pendingNotes.size());

        // Print details for debugging
//        pendingNotes.forEach(note ->
//                log.info("➡️ Pending Note ID: {}, Tenant: {}, EventType: {}, Status: {}",
//                        note.getId(),
//                        note.getTenantId(),
//                        note.getOutbox() != null ? note.getOutbox().getEventType() : "null",
//                        note.getOutbox() != null ? note.getOutbox().getStatus() : "null")
//        );

        for (UniversalCaseNote note : pendingNotes) {
            try {
                String payload = buildNotePayload(note);
                kafkaTemplate.send(note.getOutbox().getEventType(), payload);

                note.getOutbox().setStatus("DISPATCHED");

                System.out.println(payload);

                log.info("✅ Published note event for noteId={} type={}", note.getId(), note.getOutbox().getEventType());
            } catch (Exception e) {
                note.getOutbox().setStatus("FAILED");
                log.error("❌ Failed to publish noteId={} event: {}", note.getId(), e.getMessage());
            }
            noteRepository.save(note);
        }
    }

    private String buildNotePayload(UniversalCaseNote note) {
        try {
            Map<String, Object> event = new HashMap<>();
            event.put("noteId", note.getId());             //
            event.put("tenantId", note.getTenantId());         //  organisation id
            event.put("projectId", note.getProjectId());       // inside org which project so project id

            // also need to add asset_id i.e case_id
            // also need to add asset_type i.e case type   CASE_GENERAL
            event.put("caseId", note.getAssetId());  // case ID
            event.put("caseType", note.getAssetType());   // case Type

            event.put("eventType", note.getOutbox().getEventType());    //
            event.put("author",note.getAuthor());

            return objectMapper.writeValueAsString(event);
        } catch (Exception e) {
            log.error("❌ Failed to build JSON payload for noteId={}: {}", note.getId(), e.getMessage());
            return "{}";
        }
    }
}


//    private String buildNotePayload(UniversalCaseNote note) {
//        return "{ \"noteId\": \"" + note.getId() + "\", " +
//                "\"tenantId\": \"" + note.getTenantId() + "\", " +
//                "\"eventType\": \"" + note.getOutbox().getEventType() + "\", " +
//                "\"payload\": \"" + note.getOutbox().getPayload() + "\", " +
//                "\"author\": \"" + note.getAuthor() + "\" }";
//    }


//package com.cipherops.OutboundRelayService.Service;
//
//import com.cipherops.OutboundRelayService.Repository.OutboxRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//
//@Service
//@Slf4j
//public class OutboundRelayService {
//
//    @Autowired
//    private OutboxRepository outboxRepository;
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Scheduled(fixedDelay = 5000)
//    public void publishPendingEvents() {
//        List<OutboxEvent> pendingEvents = outboxRepository.findTop100ByStatusOrderByCreatedAtAsc("PENDING");
//
//        for (OutboxEvent event : pendingEvents) {
//            try {
//                kafkaTemplate.send(event.getEventType(), event.getPayload());
//                event.setStatus("DISPATCHED");
//                event.setUpdatedAt(Instant.now());
//                log.info("✅ Published event {} to Kafka", event.getEventType());
//            } catch (Exception e) {
//                log.error("❌ Failed to publish event {}", event.getId(), e);
//                event.setStatus("FAILED");
//                event.setUpdatedAt(Instant.now());
//            }
//            outboxRepository.save(event);
//        }
//    }
//}
