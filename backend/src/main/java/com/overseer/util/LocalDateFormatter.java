package com.overseer.util;

import lombok.Getter;

import java.time.format.DateTimeFormatter;

/**
 * Formatter for converting dates.
 */
@Getter
public class LocalDateFormatter {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

}
