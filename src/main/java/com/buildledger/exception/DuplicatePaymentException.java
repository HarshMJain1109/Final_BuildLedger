package com.buildledger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a payment is submitted for an invoice that already has an
 * active payment (PENDING, PROCESSING, or COMPLETED).
 * Prevents double-payment on the same invoice.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatePaymentException extends RuntimeException {

    public DuplicatePaymentException(Long invoiceId) {
        super(String.format(
                "Invoice %d already has an active or completed payment. " +
                        "Duplicate payments are not allowed. " +
                        "If the previous payment failed, update its status to FAILED before retrying.",
                invoiceId));
    }
}
