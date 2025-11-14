package com.cipherops.OutboundRelayService.enums;

public enum SocNocCaseState {
    AWAITING_CUSTOMER,
    AWAITING_OPERATOR,
    AWAITING_INTERNAL,
    ESCALATED,
    RESOLVED,
    UNKNOWN;

    public static SocNocCaseState fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return SocNocCaseState.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }
}

