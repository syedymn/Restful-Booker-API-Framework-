package com.api.endpoints;

/**
 * BookingEndpoints — Centralized API endpoint constants.
 * Keeps all endpoint URLs in one place for easy maintenance.
 * If an endpoint changes, update it here only.
 */
public class BookingEndpoints {

    // Base endpoints
    public static final String BASE_BOOKING = "/booking";
    public static final String BOOKING_BY_ID = "/booking/{id}";
    public static final String AUTH = "/auth";
    public static final String HEALTH_CHECK = "/ping";

    // Prevent instantiation
    private BookingEndpoints() {
        throw new UnsupportedOperationException(
            "This is a constants class and cannot be instantiated");
    }
}
