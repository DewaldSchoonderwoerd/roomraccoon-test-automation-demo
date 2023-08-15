package com.roomraccoon.test.automation.framework.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Custom exception class for web automation-related exceptions
public class WebAutomationException extends RuntimeException {

    // Logger for logging error messages
    private static final Logger LOG = LoggerFactory.getLogger(WebAutomationException.class);

    // Constructor to create a WebAutomationException with a specified error message
    public WebAutomationException(String message) {
        // Log the error message using the logger
        LOG.error(message);
    }
}