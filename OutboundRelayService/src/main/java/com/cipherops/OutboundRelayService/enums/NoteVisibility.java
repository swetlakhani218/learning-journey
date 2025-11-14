package com.cipherops.OutboundRelayService.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum NoteVisibility {
    INTERNAL("Internal"),
    EXTERNAL("External");

    private final String label;

    NoteVisibility(String value) {
        this.label = value;
    }

    @JsonCreator
    public static NoteVisibility fromValue(String value) {
        for (NoteVisibility v : values()) {
            if (v.name().equalsIgnoreCase(value) || v.label.equalsIgnoreCase(value)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    @JsonValue
    public String toValue() {
        return label; // ensures serialization uses "Internal"/"External"
    }
}

