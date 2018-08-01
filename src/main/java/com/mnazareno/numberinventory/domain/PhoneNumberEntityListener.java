package com.mnazareno.numberinventory.domain;

import com.mnazareno.numberinventory.service.BeanUtil;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class PhoneNumberEntityListener {

    @PrePersist
    public void prePersist(PhoneNumber target) {
        audit(target, PhoneNumberHistoryAction.CREATED);
    }

    @PreUpdate
    public void preUpdate(PhoneNumber target) {
        switch (target.getStatus()) {
            case IN_USE:
                audit(target, PhoneNumberHistoryAction.USED);
                break;
            case AVAILABLE:
                audit(target, PhoneNumberHistoryAction.RELEASED);
        }
    }

    private void audit(PhoneNumber target, PhoneNumberHistoryAction action) {
        EntityManager em = BeanUtil.getBean(EntityManager.class);
        PhoneNumberHistory history = new PhoneNumberHistory();
        history.setPhoneNumber(target);
        history.setAction(action);
        em.persist(history);
    }
}
