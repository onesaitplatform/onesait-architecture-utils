package com.minsait.architecture.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public final class DateUtils {

	private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'";
	private static final Locale LOCALE = Locale.ENGLISH;

	private DateUtils() {

	}
	
	public static String convertDateWithParameterFormat(Date date, String format) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, LOCALE);
		Instant instant = date.toInstant();
		LocalDateTime localDateTime = instant.atOffset(ZoneOffset.UTC).toLocalDateTime();
		return localDateTime.format(formatter);
	}

	public static String convertDateToStringWithFormat(Date date) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT, LOCALE);
		Instant instant = date.toInstant();
		LocalDateTime localDateTime = instant.atOffset(ZoneOffset.UTC).toLocalDateTime();
		return localDateTime.format(formatter);
	}

	public static Date generateDateNow() {
		LocalDateTime localDateTime = LocalDateTime.now();
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date clone(Date originalDate) {
		if (originalDate != null) {
			return new Date(originalDate.getTime());
		}
		return null;
	}
}
