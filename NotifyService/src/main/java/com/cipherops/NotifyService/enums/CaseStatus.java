package com.cipherops.NotifyService.enums;

import lombok.Getter;

@Getter
public enum CaseStatus {
        NEW("New"),
        WORK_IN_PROGRESS("Work in Progress"),
        PENDING_WITH_CUSTOMER("Pending with customer"),
        RESOLVED("Resolved");

        private final String dbValue;

    CaseStatus(String dbValue) {
            this.dbValue = dbValue;
        }
}
