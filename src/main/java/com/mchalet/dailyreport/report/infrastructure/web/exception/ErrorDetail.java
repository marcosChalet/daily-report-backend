package com.mchalet.dailyreport.report.infrastructure.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

/**
 * A standard, consistent structure for all API error responses.
 * @param timestamp When the error occurred.
 * @param status The HTTP status code.
 * @param error The HTTP status phrase.
 * @param message A developer-friendly message explaining the error.
 * @param path The path where the error occurred.
 * @param details An optional object containing more specific details, like validation errors.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorDetail(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Object details
) {}
