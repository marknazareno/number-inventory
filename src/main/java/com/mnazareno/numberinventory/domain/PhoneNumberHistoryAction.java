package com.mnazareno.numberinventory.domain;

public enum PhoneNumberHistoryAction {

    CREATED("CREATED"),
    USED("USED"),
    RELEASED("RELEASED");

    private final String name;

    PhoneNumberHistoryAction(String name) {
        this.name = name;
    }

    public String value() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
