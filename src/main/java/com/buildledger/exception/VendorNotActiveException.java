package com.buildledger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when an operation requires an ACTIVE vendor but the vendor's
 * current status is PENDING, SUSPENDED, or BLACKLISTED.
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class VendorNotActiveException extends RuntimeException {

    public VendorNotActiveException(Long vendorId, String currentStatus) {
        super(String.format(
                "Vendor %d is not ACTIVE (current status: %s). " +
                        "Only ACTIVE vendors are eligible for contracts. " +
                        "The vendor must complete document verification before contracts can be created.",
                vendorId, currentStatus));
    }
}
