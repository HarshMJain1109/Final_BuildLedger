package com.buildledger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a caller attempts to assign a privileged role (ADMIN, VENDOR)
 * through the standard user-creation endpoint, bypassing the proper flows.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRoleAssignmentException extends RuntimeException {

    private final String attemptedRole;

    public InvalidRoleAssignmentException(String role) {
        super(buildMessage(role));
        this.attemptedRole = role;
    }

    public String getAttemptedRole() {
        return attemptedRole;
    }

    private static String buildMessage(String role) {
        return switch (role) {
            case "ADMIN"  -> "Role ADMIN cannot be assigned through this endpoint. " +
                    "Admin accounts are provisioned through the bootstrap process only.";
            case "VENDOR" -> "Role VENDOR cannot be assigned directly. " +
                    "Vendors must register through the vendor registration flow " +
                    "and receive a user account only after admin approval.";
            default       -> "Role '" + role + "' cannot be assigned through this endpoint.";
        };
    }
}
