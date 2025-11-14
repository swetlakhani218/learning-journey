package com.cipherops.OutboundRelayService.enums;

public enum TimeRange {
    LAST_24_HOURS("Last 24h"),
    LAST_7_DAYS("Last week"),
    LAST_30_DAYS("Last month");

    private final String label;

    TimeRange(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
