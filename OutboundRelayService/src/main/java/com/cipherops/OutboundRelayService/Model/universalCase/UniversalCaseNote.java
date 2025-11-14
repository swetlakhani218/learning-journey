package com.cipherops.OutboundRelayService.Model.universalCase;

import com.cipherops.OutboundRelayService.Model.common.Outbox;
import com.cipherops.OutboundRelayService.enums.CaseType;
import com.cipherops.OutboundRelayService.enums.NoteVisibility;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "unified_notes")
public class UniversalCaseNote {
    @Id
    private String id;

    @Field(value = "tenant_id")
    private String tenantId;

    @Field(value = "project_id")
    private String projectId;

    @Field(value = "asset_type")
    private CaseType assetType;

    @Field(value = "asset_id")
    private String assetId;

    @Field(value = "parent_note_id")
    private String parentNoteId;

    private String author;

    @Getter(AccessLevel.NONE)
    @Field(value = "text_email")
    private String textEmail;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Field(value = "text_ui")
    private String textUi;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Field(value = "text_AI")
    private String textAI;

    private NoteVisibility visibility;

    @Field(value = "created_at")
    private Instant createdAt;

    @Field(value = "email_participant")
    private String emailParticipant;

    @Field(value = "mailer_name")
    private String mailerName;

    @Field("outbox")
    private Outbox outbox;

    @Transient
    public String getTextFor(String channel) {
        return switch (channel) {
            case "AI" -> hasText(textAI) ? textAI : textEmail;
            case "UI" -> hasText(textUi) ? textUi : textEmail;
            default -> textEmail;
        };
    }

    private boolean hasText(String s) {
        return s != null && !s.isBlank();
    }
}
