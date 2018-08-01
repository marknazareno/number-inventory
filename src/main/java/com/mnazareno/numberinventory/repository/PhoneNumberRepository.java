package com.mnazareno.numberinventory.repository;

import com.mnazareno.numberinventory.domain.PhoneNumber;
import com.mnazareno.numberinventory.domain.PhoneNumberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {

    Optional<PhoneNumber> findFirstByStatus(PhoneNumberStatus status);

    @Modifying
    @Query("UPDATE PhoneNumber p SET p.status = :newStatus WHERE p.phoneNo = :phoneNo AND p.status = :oldStatus")
    int updatePhoneNumber(@Param("phoneNo") Integer phoneNo, @Param("oldStatus") PhoneNumberStatus oldStatus, @Param("newStatus") PhoneNumberStatus newStatus);
}
