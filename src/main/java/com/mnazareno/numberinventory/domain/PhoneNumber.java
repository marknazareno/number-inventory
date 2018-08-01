package com.mnazareno.numberinventory.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners({PhoneNumberEntityListener.class, AuditingEntityListener.class})
public class PhoneNumber {

    @Id
    @Column
    private Integer phoneNo;

    @Column
    @CreatedDate
    private Date dateCreated;

    @LastModifiedDate
    private Date dateModified;

    @Column
    @Enumerated(EnumType.STRING)
    private PhoneNumberStatus status;

    public Integer getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Integer phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreatedz(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public PhoneNumberStatus getStatus() {
        return status;
    }

    public void setStatus(PhoneNumberStatus status) {
        this.status = status;
    }
}
