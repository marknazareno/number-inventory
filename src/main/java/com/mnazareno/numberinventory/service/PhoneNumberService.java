package com.mnazareno.numberinventory.service;

import com.mnazareno.numberinventory.domain.PhoneNumber;
import com.mnazareno.numberinventory.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface PhoneNumberService {

    List<PhoneNumber> getNumbers();

    List<PhoneNumber> addNumbers(int size);

    PhoneNumber reserveNumber(Integer phoneNumber) throws ServiceException;

    PhoneNumber useNumber(Integer phoneNumber) throws ServiceException;

    Optional<PhoneNumber> getNumber(Integer phoneNumber);

    Optional<PhoneNumber> getAndUseNextAvailableNumber() throws ServiceException;

    Optional<PhoneNumber> releaseNumber(Integer phoneNumber) throws ServiceException;

}
