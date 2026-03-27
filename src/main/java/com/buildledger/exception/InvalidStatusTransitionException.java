package com.buildledger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a requested status transition violates the defined state machine.
 * Mapped to 422 Unprocessable Entity — the request was well-formed but
 * semantically invalid given the entity's current state.
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)  // was 500 — corrected
public class InvalidStatusTransitionException extends RuntimeException {

    private final String fromStatus;
    private final String toStatus;

    public InvalidStatusTransitionException(String from, String to) {
        super(String.format("Cannot transition from status '%s' to '%s'.", from, to));
        this.fromStatus = from;
        this.toStatus   = to;
    }

    public InvalidStatusTransitionException(String from, String to, String hint) {
        super(String.format("Cannot transition from status '%s' to '%s'. %s", from, to, hint));
        this.fromStatus = from;
        this.toStatus   = to;
    }

    public String getFromStatus() { return fromStatus; }
    public String getToStatus()   { return toStatus; }
}
