package com.cipherops.OutboundRelayService.Model.universalCase;

import com.cipherops.OutboundRelayService.enums.CaseType;
import com.mongodb.lang.Nullable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "universal_case")
@Getter
@Setter
public class UniversalCase {

    @Id
    private String id;

    @Field(value = "tenant_id")
    private String tenantId;

    @Field(value = "project_id")
    private String projectId;

    @Field(value = "case_id")
    private String caseId;

    @Field(value = "case_type")
    private CaseType caseType;

    private String account;

    @Field(value = "customer_email")
    private String customerEmail;

    private String priority;

    @Field("cu_id")
    private String cuId;

    @Nullable
    private Boolean active;

//    private String title;

    @Field(value = "short_description")
    private String shortDescription;

    private String description;
    private String impact;

    @Field(value = "current_status")
    private String currentStatus;

    @Field(value = "status_reason")
    private String statusReason;

    @Field(value = "current_stage")
    private String currentStage;

    private String category;

    @Field(value = "sub_category")
    private String subcategory;

    @Field(value = "company_name")
    private String companyName;

    private Assignee assignee;
    private List<StatusHistory> statuses;

    @Field(value = "overall_sla")
    private OverallSla overallSla;

    private RFO rfo;

    @Field(value = "service_info")
    private ServiceInfo serviceInfo;

//    @Field(value = "created_by")
//    private String createdBy;

    @Field(value = "created_at")
    private Instant createdAt;

//    @Field(value = "updated_by")
//    private String updatedBy;

    @Field(value = "updated_at")
    private Instant updatedAt;

//    @Field(value = "resolved_by")
//    private String resolvedBy;
//    @Field(value = "resolved_at")
//    private Instant resolvedAt;

//    @Field(value = "opened_by")
//    private String openedBy;
//    @Field(value = "opened_at")
//    private Instant openedAt;

//    @Field(value = "closed_by")
//    private String closedBy;
//    @Field(value = "closed_at")
//    private Instant closedAt;

//    @Field(value = "watch_list")
//    private String watchList;
//    @Field(value = "resolution_notes")
//    private String resolutionNotes;

    @Builder.Default
    @Field("is_pinned")
    private Boolean isPinned = false;

    @Field(value = "note_ids")
    private List<String> noteIds;

    @Builder.Default
    @Field(value = "version")
    private Long version = 0L;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Assignee {
        private String type;
        private String id;
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatusHistory {
        private String status;
        private String stage;

        @Field(value = "entered_at")
        private Instant enteredAt;

        @Field(value = "exited_at")
        private Instant exitedAt;

        @Field(value = "sla_config")
        private SlaConfig slaConfig;

        private List<Assignment> assignments;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SlaConfig {
        @Field(value = "target_duration_minutes")
        private Integer targetDurationMinutes;

        @Field(value = "actual_duration_minutes")
        private Integer actualDurationMinutes;

        private Boolean breached;

        @Field(value = "breach_time")
        private Integer breachTime;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceInfo {
        @Field(value = "service_identifier")
        private String serviceIdentifier;

        @Field(value = "service_downtime")
        private String serviceDowntime;

        @Field(value = "service_down_start")
        private String serviceDownStart;

        @Field(value = "service_down_end")
        private String serviceDownEnd;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Assignment {
        @Field(value = "user_id")
        private String userId;

        @Field(value = "user_name")
        private String userName;

        @Field(value = "user_group")
        private String userGroup;

        @Field(value = "assigned_at")
        private String assignedAt;

        @Field(value = "unassigned_at")
        private Instant unassignedAt;

        @Field(value = "time_spent_minutes")
        private Integer timeSpentMinutes;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OverallSla {
        @Field(value = "status_level_breaches")
        private Integer statusLevelBreaches;

        @Field(value = "overall_breached")
        private Boolean overallBreached;

        @Field(value = "total_breach_time")
        private Integer totalBreachTime;

        @Field(value = "total_outage_duration")
        private Integer totalOutageDuration;

        @Field(value = "total_service_downtime")
        private Integer totalServiceDowntime;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RFO {
        private String cause;
        private String details;
    }
}