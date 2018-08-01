package com.mnazareno.numberinventory.web.model;

import com.mnazareno.numberinventory.domain.PhoneNumberStatus;

public class PhoneNumberModel {

    private PhoneNumberStatus status;

    public PhoneNumberStatus getStatus() {
        return status;
    }

    public void setStatus(PhoneNumberStatus status) {
        this.status = status;
    }
}
