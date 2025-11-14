package com.cipherops.NotifyService.enums;

public enum CaseType {
    CASE_DND, CASE_GENERAL, CASE_ALERT, SMS;

    public static com.cipherops.NotifyService.enums.SocNocCategory socNocCategory(CaseType category) {
        return category == CASE_DND ? com.cipherops.NotifyService.enums.SocNocCategory.DND : com.cipherops.NotifyService.enums.SocNocCategory.SMS;
    }

    public static CaseType fromCaseType(String caseType) {
        if (caseType == null) return CASE_GENERAL;
        return switch (caseType.trim().toUpperCase()) {
            case "CASE_DND" -> CASE_DND;
            case "CASE_GENERAL" -> CASE_GENERAL;
            case "CASE_ALERT"-> CASE_ALERT;
            default -> SMS;
        };
    }
}
