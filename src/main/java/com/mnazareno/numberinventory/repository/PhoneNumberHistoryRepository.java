package com.mnazareno.numberinventory.repository;

import com.mnazareno.numberinventory.domain.PhoneNumberHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberHistoryRepository extends JpaRepository<PhoneNumberHistory, Long> {
}
