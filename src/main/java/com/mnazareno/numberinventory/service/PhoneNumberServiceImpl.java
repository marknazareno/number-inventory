package com.mnazareno.numberinventory.service;

import com.mnazareno.numberinventory.domain.PhoneNumber;
import com.mnazareno.numberinventory.domain.PhoneNumberHistory;
import com.mnazareno.numberinventory.domain.PhoneNumberHistoryAction;
import com.mnazareno.numberinventory.domain.PhoneNumberStatus;
import com.mnazareno.numberinventory.exception.ServiceException;
import com.mnazareno.numberinventory.repository.PhoneNumberHistoryRepository;
import com.mnazareno.numberinventory.repository.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    private PhoneNumberHistoryRepository historyRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<PhoneNumber> addNumbers(int size) {
        int min = 80000000;
        int max = 99999999;
        Random r = new Random();

        List<PhoneNumber> phoneNumbers = IntStream
                .rangeClosed(1, size)
                .parallel()
                .map(x -> r.nextInt(max + 1 - min) + min)
                .mapToObj(x -> {
                    PhoneNumber phoneNumber = new PhoneNumber();
                    phoneNumber.setPhoneNo(x);
                    phoneNumber.setStatus(PhoneNumberStatus.AVAILABLE);
                    return phoneNumber;
                })
                .collect(Collectors.toList());

        return phoneNumberRepository.saveAll(phoneNumbers);
    }

    @Override
    public List<PhoneNumber> getNumbers() {
        PhoneNumber filter = new PhoneNumber();
        filter.setStatus(PhoneNumberStatus.AVAILABLE);
        return phoneNumberRepository.findAll(Example.of(filter));
    }

    @Override
    public Optional<PhoneNumber> getNumber(Integer phoneNumber) {
        return phoneNumberRepository.findById(phoneNumber);
    }

    @Override
    @Transactional
    public Optional<PhoneNumber> getAndUseNextAvailableNumber() throws ServiceException {
        Optional<PhoneNumber> result = Optional.empty();
        int updated = 0;

        while (updated == 0) {
            result = phoneNumberRepository.findFirstByStatus(PhoneNumberStatus.AVAILABLE);

            if (!result.isPresent()) break;

            updated = phoneNumberRepository.updatePhoneNumber(result.get().getPhoneNo(), PhoneNumberStatus.AVAILABLE, PhoneNumberStatus.IN_USE);
            entityManager.refresh(result.get());

            PhoneNumberHistory history = new PhoneNumberHistory();
            history.setPhoneNumber(result.get());
            history.setAction(PhoneNumberHistoryAction.USED);
            historyRepository.save(history);
        }

        return result;
    }

    @Override
    @Transactional
    public PhoneNumber reserveNumber(Integer phoneNumber) throws ServiceException {
        PhoneNumber filter = new PhoneNumber();
        filter.setPhoneNo(phoneNumber);
        filter.setStatus(PhoneNumberStatus.AVAILABLE);

        PhoneNumber pn = phoneNumberRepository
                .findOne(Example.of(filter))
                .orElseThrow(() -> new ServiceException("Number not available"));

        pn.setStatus(PhoneNumberStatus.RESERVED);

        return pn;
    }

    @Override
    @Transactional
    public PhoneNumber useNumber(Integer phoneNumber) throws ServiceException {
        PhoneNumber filter = new PhoneNumber();
        filter.setPhoneNo(phoneNumber);
        filter.setStatus(PhoneNumberStatus.RESERVED);

        PhoneNumber pn = phoneNumberRepository
                .findOne(Example.of(filter))
                .orElseThrow(() -> new ServiceException("Number not reserved"));

        pn.setStatus(PhoneNumberStatus.IN_USE);

        return pn;
    }

    @Override
    @Transactional
    public Optional<PhoneNumber> releaseNumber(Integer phoneNumber) throws ServiceException {
        Optional<PhoneNumber> result = phoneNumberRepository.findById(phoneNumber);
        result.ifPresent(p -> p.setStatus(PhoneNumberStatus.AVAILABLE));
        return result;
    }
}
