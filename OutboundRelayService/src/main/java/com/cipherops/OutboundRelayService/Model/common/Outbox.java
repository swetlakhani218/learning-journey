package com.cipherops.OutboundRelayService.Model.common;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Outbox {

    @Field("eventType")
    private String eventType;

    @Field("status")
    private String status;

    @Field("createdAt")
    private Instant createdAt;

/*
    @Field("updatedAt")
    private Instant updatedAt;  // Matches Mongo field
*/

}
