package com.mnazareno.numberinventory.domain;

public enum PhoneNumberStatus {

    AVAILABLE("AVAILABLE"),
    IN_USE("USED"),
    RESERVED("RESERVED");

    private final String name;

    PhoneNumberStatus(String name) {
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
