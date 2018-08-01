package com.mnazareno.numberinventory.web.controller;

import com.mnazareno.numberinventory.domain.PhoneNumber;
import com.mnazareno.numberinventory.exception.ServiceException;
import com.mnazareno.numberinventory.service.PhoneNumberService;
import com.mnazareno.numberinventory.web.model.PhoneNumberModel;
import com.mnazareno.numberinventory.web.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PhoneNumberController {

    @Autowired
    private PhoneNumberService phoneNumberService;

    /**
     * Returns a list of all available phone numbers
     *
     * @return
     */
    @GetMapping("/numbers")
    public Response getNumbers() {
        return new Response(phoneNumberService.getNumbers());
    }

    /**
     * Returns the next available phone number and mark it as "in use"
     *
     * @return
     */
    @GetMapping("/numbers/available")
    public ResponseEntity<PhoneNumber> getNumber() throws ServiceException {
        return phoneNumberService.getAndUseNextAvailableNumber().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Returns info on a phone number
     *
     * @param number
     * @return
     */
    @GetMapping("/numbers/{number}")
    public ResponseEntity<PhoneNumber> getNumber(@PathVariable Integer number) {
        return phoneNumberService.getNumber(number)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates a phone number to:
     * 2 = reserved
     * 0 = in_use
     *
     * @param number
     * @param model
     * @return
     * @throws ServiceException
     */
    @PutMapping(value = "/numbers/{number}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PhoneNumber> updateNumber(@PathVariable Integer number, @RequestBody PhoneNumberModel model) throws ServiceException {
        switch (model.getStatus()) {
            case AVAILABLE:
                return phoneNumberService.releaseNumber(number)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
            case RESERVED:
                return ResponseEntity.ok(phoneNumberService.reserveNumber(number));
            default:
                return ResponseEntity.ok(phoneNumberService.useNumber(number));
        }
    }
}
